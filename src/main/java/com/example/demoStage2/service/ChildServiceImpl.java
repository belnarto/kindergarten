package com.example.demoStage2.service;

import static org.springframework.util.StringUtils.hasText;

import com.example.demoStage2.dto.Child;
import com.example.demoStage2.entity.ChildEntity;
import com.example.demoStage2.entity.UserEntity;
import com.example.demoStage2.exception.NotAllowedException;
import com.example.demoStage2.repository.ChildRepository;
import com.example.demoStage2.repository.UserRepository;
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
    public long save(Child childDto) {
        ChildEntity childEntity = new ChildEntity();
        setCommonFieldsFromDtoToEntity(childDto, childEntity);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("user not found"));

        childEntity.setUser(user);

        ChildEntity savedChildEntity = childRepository.save(childEntity);
        return savedChildEntity.getId();
    }

    @Override
    public Child get(Long id) {
        Optional<ChildEntity> childByIdOptional = childRepository.findById(id);

        if (childByIdOptional.isEmpty()) {
            return null;
        }

        ChildEntity childEntity = childByIdOptional.get();

        Child child = new Child();
        child.setFirstName(childEntity.getFirstName());
        child.setLastName(childEntity.getLastName());
        child.setAge(childEntity.getAge());
        child.setSex(childEntity.getSex());
        child.setContactPhones(childEntity.getContactPhones());

        return child;
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
    public boolean update(Long id, Child childDto) {
        if (childRepository.existsById(id)) {
            if (isChildOwnedByUser(id, SecurityContextHolder.getContext().getAuthentication())) {
                ChildEntity childEntity = new ChildEntity();
                childEntity.setId(id);
                setCommonFieldsFromDtoToEntity(childDto, childEntity);
                childRepository.save(childEntity);
                return true;
            } else {
                throw new NotAllowedException();
            }
        }
        return false;
    }

    @Override
    public List<Child> searchByCategory(String category) {
        return childRepository.findByCategoryIgnoreCaseOrderByUpdatedAtDesc(category).stream()
            .map(this::setCommonFieldsFromEntityToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<Child> searchByName(String name) {
        return childRepository.findByPartName(name).stream()
            .map(this::setCommonFieldsFromEntityToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<Child> searchByAge(int minAge, int maxAge) {
        return childRepository.findByAgeGreaterThanAndAgeLessThanEqualOrderByAgeAsc(minAge, maxAge).stream()
            .map(this::setCommonFieldsFromEntityToDto)
            .collect(Collectors.toList());
    }

    private void setCommonFieldsFromDtoToEntity(Child childDto, ChildEntity childEntity) {
        childEntity.setFirstName(childDto.getFirstName());
        childEntity.setLastName(childDto.getLastName());
        childEntity.setAge(childDto.getAge());
        childEntity.setSex(childDto.getSex());
        childEntity.setContactPhones(childDto.getContactPhones());
        childEntity.setCategory(childDto.getCategory());
        childEntity.setBirthdate(childDto.getBirthdate());
        childEntity.setUpdatedAt(LocalDateTime.now());
    }

    private Child setCommonFieldsFromEntityToDto(ChildEntity childEntity) {
        return Child.builder()
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
