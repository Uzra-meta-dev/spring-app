package com.javalin.spring.service;

import com.javalin.spring.database.entity.Company;
import com.javalin.spring.database.repository.CompanyRepository;
import com.javalin.spring.dto.CompanyReadDto;
import com.javalin.spring.listener.entity.AccessType;
import com.javalin.spring.listener.entity.EntityEvent;
import com.javalin.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("comService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CompanyReadMapper companyReadMapper;



    public Optional<CompanyReadDto> findById(Integer id){
        // TODO 24.09.2025 AntiMeta
       return companyRepository
               .findById(id)
               .map(entity ->{
                       eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                      return companyReadMapper.map(entity);
               });
    }

    public List<CompanyReadDto> findAll(){
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
