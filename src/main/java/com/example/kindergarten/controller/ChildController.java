package com.example.kindergarten.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.example.kindergarten.dto.ChildDto;
import com.example.kindergarten.exception.NotAllowedException;
import com.example.kindergarten.service.ChildService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RequiredArgsConstructor
@RestController
public class ChildController {

    private final ChildService service;

    @PostMapping("/api/child/new")
    public Map<String, Long> saveChild(@Valid @RequestBody ChildDto childDto) {
        Long id = service.save(childDto);
        return Map.of("id", id);
    }

    @GetMapping("/api/child/{firstName}/{lastName}")
    public ResponseEntity<ChildDto> getChild(@PathVariable String firstName, @PathVariable String lastName) {
        ChildDto childDto = service.get(firstName, lastName);
        if (childDto == null) {
            return new ResponseEntity<>(NOT_FOUND);
        } else {
            return ResponseEntity.ok(childDto);
        }
    }

    @GetMapping("/api/child/{id}")
    public ResponseEntity<ChildDto> getChildById(@PathVariable Long id) {
        ChildDto childDto = service.get(id);
        if (childDto == null) {
            return new ResponseEntity<>(NOT_FOUND);
        } else {
            return ResponseEntity.ok(childDto);
        }
    }

    @DeleteMapping("/api/child/{id}")
    public ResponseEntity<Void> removeChildById(@PathVariable Long id) {
        try {
            if (service.remove(id)) {
                return new ResponseEntity<>(NO_CONTENT);
            } else {
                return new ResponseEntity<>(NOT_FOUND);
            }
        } catch (NotAllowedException notAllowedException) {
            return new ResponseEntity<>(FORBIDDEN);
        }
    }

    @GetMapping("/api/child")
    public Collection<ChildDto> getAllChildrenBySex(@RequestHeader(name = "sex", required = false) String sex) {
        return service.getAllChildrenBySex(sex);
    }

    @PutMapping("/api/child/{id}")
    public ResponseEntity<Void> updateChildById(@PathVariable Long id, @RequestBody ChildDto childDto) {
        try {
            if (service.update(id, childDto)) {
                return new ResponseEntity<>(NO_CONTENT);
            } else {
                return new ResponseEntity<>(NOT_FOUND);
            }
        } catch (NotAllowedException notAllowedException) {
            return new ResponseEntity<>(FORBIDDEN);
        }
    }

    @GetMapping("/api/child/search")
    public ResponseEntity<List<ChildDto>> search(@RequestParam(required = false, name = "category") String category,
                                                 @RequestParam(required = false, name = "name") String name) {

        if ((category == null && name == null) || (category != null && name != null)) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        if (category != null) {
            return ResponseEntity.ok(service.searchByCategory(category));
        } else {
            return ResponseEntity.ok(service.searchByName(name));
        }
    }

    @GetMapping("/api/child/search-by-age")
    public ResponseEntity<List<ChildDto>> searchByAge(@RequestParam(name = "minAge") int minAge,
                                                      @RequestParam(name = "maxAge") int maxAge) {

        if (!(minAge > 0 && maxAge > minAge)) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        return ResponseEntity.ok(service.searchByAge(minAge, maxAge));
    }
}
