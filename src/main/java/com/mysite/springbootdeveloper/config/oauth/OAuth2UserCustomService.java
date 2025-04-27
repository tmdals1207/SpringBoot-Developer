package com.mysite.springbootdeveloper.config.oauth;

import lombok.RequiredArgsConstructor;
import com.mysite.springbootdeveloper.domain.User;
import com.mysite.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest); // ❶ 요청을 바탕으로 유저 정보를 담은 객체 반환
        // OAuth2 공급자 구분: 구글 또는 카카오
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 구글 로그인이면 구글용 로직, 카카오 로그인일 경우 카카오용 로직
        if ("google".equals(registrationId)) {
            saveOrUpdateForGoogle(user, registrationId);
        } else if ("kakao".equals(registrationId)) {
            saveOrUpdateForKakao(user, registrationId);
        }

        return user;
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    // 구글 로그인에 대한 처리
    private void saveOrUpdateForGoogle(OAuth2User oAuth2User, String provider) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String password = passwordEncoder.encode(UUID.randomUUID().toString());

        User user = userRepository.findByEmailAndProvider(email, provider)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .password(password)
                        .provider(provider)
                        .build());

        userRepository.save(user);
    }

    // ❷ 유저가 있으면 업데이트, 없으면 유저 생성
    // 카카오 로그인에 대한 처리
    private void saveOrUpdateForKakao(OAuth2User oAuth2User, String provider) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 카카오 계정 정보에서 이메일 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        String name = (String) ((Map<String, Object>) attributes.get("properties")).get("nickname");
        String password = passwordEncoder.encode(UUID.randomUUID().toString());

        User user = userRepository.findByEmailAndProvider(email, provider)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .password(password)
                        .provider(provider)
                        .build());

        userRepository.save(user);
    }
//    private void saveOrUpdate(OAuth2User oAuth2User) {
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        System.out.println(attributes);
//
//        String email = (String) attributes.get("email");
//        String name = (String) attributes.get("name");
//        String password = passwordEncoder.encode(UUID.randomUUID().toString());
//
//        User user = userRepository.findByEmail(email)
//                .map(entity -> entity.update(name))
//                .orElse(User.builder()
//                        .email(email)
//                        .nickname(name)
//                        .password(password)
//                        .build());
//
//        userRepository.save(user);
//    }
}