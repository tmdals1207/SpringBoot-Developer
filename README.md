<h1>🌟 프로젝트 소개</h1>

<p>
이 프로젝트는 <b>Spring Boot</b> 기반으로 <b>Spring Security</b>, <b>JWT(Json Web Token)</b>, <b>OAuth2</b> 인증을 적용하여
웹 페이지를 개발하고, 이를 통해 실습하고 학습하는 것을 목표로 합니다.
</p>

<p>
기본적인 로그인/회원가입 기능부터 소셜 로그인(OAuth2) 연동까지 직접 구현하여,
보안과 인증에 대한 이해를 높이고자 했습니다.
</p>

<br>

<h1>✨ 주요 기술 스택</h1>

<ul>
<li><b>Spring Boot</b>: 서버 어플리케이션 개발</li>
<li><b>Spring Security</b>: 인증 및 인가 기능 구현</li>
<li><b>JWT (Json Web Token)</b>: 토큰 기반 인증 시스템 구축</li>
<li><b>OAuth2</b>: Google, Kakao 등 외부 소셜 로그인 연동</li>
<li><b>JPA (Hibernate)</b>: 데이터베이스 ORM 매핑</li>
<li><b>My SQL</b>: 로컬 테스트용 데이터베이스</li>
<li><b>Thymeleaf</b>: 웹 페이지 렌더링 </li>
</ul>

<br>

<h1>🔒 주요 기능</h1>

<ul>
<li><b>회원가입 및 로그인</b></li>
<ul>
  <li>이메일/비밀번호를 통한 기본 로그인</li>
  <li>로그인 성공 시 JWT 발급 및 관리</li>
</ul>
<li><b>JWT 인증 및 인가 처리</b></li>
<ul>
  <li>모든 API 요청에 대해 Access Token을 통한 사용자 인증</li>
  <li>만료된 토큰에 대해 Refresh Token으로 재발급</li>
</ul>
<li><b>OAuth2 소셜 로그인</b></li>
<ul>
  <li>Google, Kakao 소셜 로그인 지원</li>
  <li>소셜 로그인 이후 사용자 정보 저장 및 추가 회원가입 처리</li>
</ul>
<li><b>인증/인가 예외 처리 및 에러 페이지 제공</b></li>
</ul>

<br>

<h1>📚 학습 포인트</h1>

<ul>
<li>Spring Security를 직접 설정하며 인증 흐름을 이해</li>
<li>JWT를 발급하고 필터를 통해 토큰 인증 처리</li>
<li>OAuth2 로그인 프로세스 이해 및 연동 실습</li>
<li>JWT와 OAuth를 함께 사용하는 복합 인증 시스템 설계 경험</li>
</ul>
