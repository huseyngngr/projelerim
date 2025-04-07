package com.healthylife.controller;

import com.healthylife.dto.UserDTO;
import com.healthylife.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@Api(tags = "Kullanıcı İşlemleri")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Tüm kullanıcıları listeler (Sadece ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Kullanıcılar başarıyla listelendi", response = UserDTO.class, responseContainer = "List"),
        @ApiResponse(code = 403, message = "Bu işlem için yetkiniz yok"),
        @ApiResponse(code = 500, message = "Sunucu hatası")
    })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @ApiOperation("Belirli bir kullanıcının bilgilerini getirir")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Kullanıcı bilgileri başarıyla getirildi", response = UserDTO.class),
        @ApiResponse(code = 403, message = "Bu işlem için yetkiniz yok"),
        @ApiResponse(code = 404, message = "Kullanıcı bulunamadı"),
        @ApiResponse(code = 500, message = "Sunucu hatası")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @ApiOperation("Kullanıcı bilgilerini günceller")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Kullanıcı başarıyla güncellendi", response = UserDTO.class),
        @ApiResponse(code = 400, message = "Geçersiz istek"),
        @ApiResponse(code = 403, message = "Bu işlem için yetkiniz yok"),
        @ApiResponse(code = 404, message = "Kullanıcı bulunamadı"),
        @ApiResponse(code = 500, message = "Sunucu hatası")
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Kullanıcıyı siler (Sadece ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Kullanıcı başarıyla silindi"),
        @ApiResponse(code = 403, message = "Bu işlem için yetkiniz yok"),
        @ApiResponse(code = 404, message = "Kullanıcı bulunamadı"),
        @ApiResponse(code = 500, message = "Sunucu hatası")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
} 