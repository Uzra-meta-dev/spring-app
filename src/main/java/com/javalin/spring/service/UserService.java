package com.javalin.spring.service;

import com.javalin.spring.database.entity.User;
import com.javalin.spring.database.repository.UserRepository;
import com.javalin.spring.dto.UserCreateEditDto;
import com.javalin.spring.dto.UserCreateOauthDto;
import com.javalin.spring.dto.UserReadDto;
import com.javalin.spring.mapper.UserCreateEditMapper;
import com.javalin.spring.mapper.UserCreateOathMapper;
import com.javalin.spring.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final ImageService imageService;
    private final UserCreateOathMapper userCreateOathMapper;


//    @PostFilter("filterObject.role.name().equals('ADMIN')")
//    @PostFilter("@comService.findById(filterObject.company.id()).isPresent()")
    public List<UserReadDto> findAll(){
        return ((JpaRepository<User, Long>) userRepository).findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto){
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                       return userCreateEditMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();

    }
    @Transactional
    public UserReadDto create(UserCreateOauthDto userDto){
        return Optional.of(userDto)
                .map(userCreateOathMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();

    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto){
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userCreateEditDto.getImage());
                     return userCreateEditMapper.map(userCreateEditDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()){
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public boolean delete(Long id){
                return userRepository.findById(id)
                        .map(entity -> {
                            userRepository.delete(entity);
                            userRepository.flush();
                            return true;
                        })
                        .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user-> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                )).orElseThrow(()-> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}
