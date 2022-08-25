package com.example.kindergarten.service;

import static org.springframework.util.StringUtils.hasText;

import com.example.kindergarten.dto.ChildDto;
import com.example.kindergarten.entity.ChildEntity;
import com.example.kindergarten.entity.UserEntity;
import com.example.kindergarten.exception.NotAllowedException;
import com.example.kindergarten.repository.ChildRepository;
import com.example.kindergarten.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChildServiceImpl implements ChildService {

    private final ChildRepository childRepository;
    private final UserRepository userRepository;

    @Override
    public long save(ChildDto childDto) {
        ChildEntity childEntity = setCommonFieldsFromDtoToEntity(childDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("user not found"));

        childEntity.setUser(user);

        ChildEntity savedChildEntity = childRepository.save(childEntity);
        return savedChildEntity.getId();
    }

    @Override
    public ChildDto get(Long id) {
        Optional<ChildEntity> childByIdOptional = childRepository.findById(id);

        if (childByIdOptional.isEmpty()) {
            return null;
        }

        ChildEntity childEntity = childByIdOptional.get();

        return ChildDto.builder()
                .firstName(childEntity.getFirstName())
                .lastName(childEntity.getLastName())
                .age(childEntity.getAge())
                .sex(childEntity.getSex())
                .contactPhones(childEntity.getContactPhones())
                .category(childEntity.getCategory())
                .birthdate(childEntity.getBirthdate())
                .updatedAt(childEntity.getUpdatedAt())
                .build();
    }

    @Override
    public boolean remove(Long id) {
        if (childRepository.existsById(id)) {
            if (isChildOwnedByUser(id, SecurityContextHolder.getContext().getAuthentication())) {
                childRepository.deleteById(id);
                return true;
            } else {
                throw new NotAllowedException();
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean update(Long id, ChildDto childDto) {
        if (childRepository.existsById(id)) {
            if (isChildOwnedByUser(id, SecurityContextHolder.getContext().getAuthentication())) {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new RuntimeException("user not found"));

                ChildEntity childEntity = setCommonFieldsFromDtoToEntity(childDto);
                childEntity.setUser(user);
                childEntity.setId(id);
                childRepository.save(childEntity);
                return true;
            } else {
                throw new NotAllowedException();
            }
        }
        return false;
    }

    @Override
    public List<ChildDto> searchByCategory(String category) {
        return childRepository.findByCategoryIgnoreCaseOrderByUpdatedAtDesc(category).stream()
                .map(this::setCommonFieldsFromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChildDto> searchByName(String name) {
        return childRepository.findByFirstNameContainingIgnoreCaseOrderByUpdatedAtDesc(name).stream()
                .map(this::setCommonFieldsFromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChildDto> searchByAge(int minAge, int maxAge) {
        return childRepository.findByAgeGreaterThanAndAgeLessThanEqualOrderByAgeAsc(minAge, maxAge).stream()
                .map(this::setCommonFieldsFromEntityToDto)
                .collect(Collectors.toList());
    }

    private ChildEntity setCommonFieldsFromDtoToEntity(ChildDto childDto) {
        return ChildEntity.builder()
                .firstName(childDto.getFirstName())
                .lastName(childDto.getLastName())
                .category(childDto.getCategory())
                .age(childDto.getAge())
                .birthdate(childDto.getBirthdate())
                .contactPhones(childDto.getContactPhones())
                .sex(childDto.getSex())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private ChildDto setCommonFieldsFromEntityToDto(ChildEntity childEntity) {
        return ChildDto.builder()
                .firstName(childEntity.getFirstName())
                .lastName(childEntity.getLastName())
                .category(childEntity.getCategory())
                .age(childEntity.getAge())
                .birthdate(childEntity.getBirthdate())
                .contactPhones(childEntity.getContactPhones())
                .sex(childEntity.getSex())
                .updatedAt(childEntity.getUpdatedAt())
                .build();
    }

    private boolean isChildOwnedByUser(Long childId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return childRepository.findById(childId)
                .map(childEntity -> childEntity.getUser() != null &&
                        hasText(childEntity.getUser().getUsername()) &&
                        childEntity.getUser().getUsername().equalsIgnoreCase(userDetails.getUsername()))
                .orElse(false);
    }
}
