package com.capstone.server.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.capstone.server.DTO.RequestDTO.JoinRequestDTO;
import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.DTO.UserDTO;
import com.capstone.server.Domain.User;
import com.capstone.server.Exception.ApiException;
import com.capstone.server.Exception.ExceptionEnum;
import com.capstone.server.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private PasswordEncoder bCryptPasswordEncoder;
    @Value("${spring.s3.bucket}")
    private String bucketName;
    private final AmazonS3Client amazonS3Client;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, TokenService tokenService, AmazonS3Client amazonS3Client){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.amazonS3Client = amazonS3Client;
    }

    public void join(JoinRequestDTO user){
        if (userRepository.existsByUserNm(user.getNickname())){
            throw new ApiException(ExceptionEnum.NICKNAME_DUPLICATE);
        }else if (userRepository.existsById(user.getId())){
            throw new ApiException(ExceptionEnum.ID_DUPLICATE);
        }
        //아이디, 이메일, 닉네임 중 중복된 값이 없으면 회원가입 가능함

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if(user.getRole() == ""){
            user.setRole("ROLE_USER");
        }

        userRepository.save(User.builder()
                .id(user.getId())
                .userNm(user.getNickname())
                .userPw(user.getPassword())
                .joinDt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .userPhotoIn("")
                .userExp(0)
                .userLevel("L1")
                .role(user.getRole())
                .build());

        tokenService.saveToken(TokenDTO.builder()
                .userId(user.getId())
                .token("")
                .status(false)
                .build());
    }

    public void autoLogin(String userId){
        //이후 fcmToken등의 부가적인 처리를 할 가능성이 있기 때문에, controller단에서 바로 tokenService.getLoginStatus를 부르지 않고
        //userService에서 tokenService를 부르는 형식으로 구현함.
        //로그아웃 처리 방식에 따라 달라지기 때문에, auto login의 경우 login status 처리가 필요없을 수도 있음. (차후에 수정)
        tokenService.setLoginStatus(userId);
    }

    public UserDTO getUserInfo(String userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getUserNm(), user.get().getUserPw(), user.get().getJoinDt(), user.get().getUserLevel(), user.get().getUserExp(), user.get().getUserPhotoIn(), user.get().getRole());
            return userDTO;
        }else{
            throw new ApiException(ExceptionEnum.BAD_REQUEST);
        }
    }

    public String uploadFile(String id, MultipartFile file){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        String uploadFileUrl = "";
        try(InputStream inputStream = file.getInputStream()){
            String keyName = "profile/"+UUID.randomUUID().toString()+"."+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1);
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
            uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();

            User user = userRepository.findById(id).get();
            UserDTO userDTO = new UserDTO();
            userDTO = userDTO.fromEntity(user);
            userDTO.setUserPhotoIn(uploadFileUrl);
            userRepository.save(userDTO.toEntity());

        }catch(IOException e){
            throw new ApiException(ExceptionEnum.BAD_REQUEST);
        }

        return uploadFileUrl;

    }

}

