# Yanoja Clone (FE & BE 협업)

<p align="center">
  <img src="public/favicon.ico"  width="150" height="150"/><br/>
  <a href="/">
    <img src="https://img.shields.io/badge/Yanolja Clone-red?style=for-the-badge&logoColor=white" alt="Yanolja Clone"/>
  </a>
  <a href="https://github.com/Yanolja-MiniProject-10/KDT_Y_BE_Mini-Project">
    <img src="https://img.shields.io/badge/배포 repository-212125?style=for-the-badge&logoColor=white" alt="배포 레포"/>
  </a>
  <br/>
</p>

```
Test ID : test@yanolja.com
Test PW : 00000
API 문서 링크 : https://ybe-mini.site/swagger-ui/index.html
```

<br/>

<details>
<summary>[요구 사항 체크]</summary>

  <details>
  <summary>a. 프로젝트 요구 사항</summary>

[회원 회원가입 기능]

✅  회원은 회원가입을 할 수 있습니다.
❎  기본 정보는 ID 역할로 이메일 주소와, 비밀번호, 이름 입니다.

[회원 로그인 기능]

- [ ]  이메일과 비밀번호로 로그인할 수 있습니다.
- [ ]  회원 정보를 저장해둔 데이터베이스를 검색하여 해당 사용자가 유효한 사용자 인지 판단합니다.
- [ ]  상품 조회(전체, 개별), 회원 가입은 로그인 없이 사용 가능합니다. 이 외 기능은 로그인이 필요합니다.

[전체 상품 목록 조회]

- [ ]  데이터베이스에서 전체 상품 목록을 가져옵니다.
- [ ]  이미지, 상품명, 상품가격을 기본으로 출력합니다.
- [ ]  재고에 따라 품절일 경우, 출력 여부에 대해선 팀별로 결정합니다.
- [ ]  (옵션) 카테고리를 분류하여, 상품을 출력할 수도 있습니다.
- [ ]  한 페이지에 출력되는 상품 개수는 팀별로 정하여, 페이징을 수행합니다.

[개별 상품 조회]

- [ ]  전체 상품 목록에서 특정 상품 이미지를 클릭하면, 해당 상품에 대한 상세 정보를 상품에 저장해 둔 데이터베이스에서 가져옵니다.
- [ ]  이미지, 상품명, 상품가격, 상품 상세 소개 (1줄 이상)을 기본으로 출력합니다.
- [ ]  재고에 따라 품절일 경우, 화면 구성은 팀별로 결정합니다.

[상품 옵션 선택]

- [ ]  상품 상세 소개 페이지에서 상품 옵션을 선택할 수 있습니다.
- [ ]  날짜, 숙박 인원은 기본으로 포함됩니다.
- [ ]  이 외 룸 형태 등 필요한 요소는 팀별로 기획합니다.

[장바구니 담기]

- [ ]  상품 옵션을 선택한 후, 장바구니 담기 버튼을 클릭하면 선택한 상품이 장바구니에 담깁니다.

[장바구니 보기]

- [ ]  장바구니에 담긴 상품 데이터 (이미지, 상품명, 옵션 등)에 따른 상품별 구매 금액, 전체 주문 합계 금액 등을 화면에 출력합니다.
- [ ]  체크 박스를 통해 결제할 상품을 선택/제외할 수도 있습니다.
- [ ]  주문하기 버튼을 통해 주문/결제 화면으로 이동합니다.

[주문하기]

- [ ]  장바구니에서 주문하기 버튼 또는 개별 상품 조회 페이지에서 주문하기 버튼을 누르면 전환되는 페이지입니다.
- [ ]  만 14세 이상 이용 동의를 체크 박스로 입력 받으면, 화면 최하단에 결제하기 버튼이 활성화됩니다.

[결제하기]

- [ ]  주문 페이지에서 결제하기 버튼을 클릭하면, 실제 결제 로직 및 절차 없이 상품을 바로
  주문한 것으로 처리합니다.
- [ ]  주문을 저장하는 데이터베이스에 주문 정보를 저장합니다.

[주문 결과 확인]

- [ ]  결제를 성공적으로 처리하면, 주문한 상품(들)에 대한 주문 결과를 출력해줍니다.

[(옵션) 주문 내역 확인]

- [ ]  별도 주문 내역 페이지에 여태 주문한 모든 이력을 출력해줍니다.
  </details>

  <details>
  <summary>b. 기능적 요구 사항</summary>
  [공통]

- [ ]  모든 단계에서 협업을 기반으로 프로젝트를 진행합니다.
- [ ]  각 기능을 구현하기 위해 HTTP Request Body / Response Body 에 전달할 데이터는 프론트엔드와 백엔드의 협업을 통해 결정합니다.
- [ ]  모든 단계에서 **테스트를 수행**합니다.

[프론트엔드]

- [ ]  사용자 인터페이스 예시를 참고하여, 화면을 구성합니다.
- [ ]  API 명세에 따라 백엔드에 전달된 JSON 데이터를 필요에 따라 정돈하여 화면에 출력합니다.
- [ ]  프론트엔드 단에서 **유효성 검사**를 수행해야하는 지점을 고려합니다.
- [ ]  React.js 또는 Next.js를 기반으로 구현하며, 컴포넌트 단위로 구조를 설계합니다.
- [ ]  (옵션) 페이징 처리 시, **무한 스크롤**을 고려합니다.

  </details>

  <details>
  <summary>c. 평가 항목 분석</summary>
  [프로젝트 관리]

- [ ]  일정을 설정하고 업무를 효과적으로 분배하고 관리했는가?
- [ ]  일정 계획 및 관리, 업무 분배, 이슈 관리 등 프로젝트 관리에 충실히 노력했는가?

[설계]

- [ ]  사용자 경험(UX)을 고려한 사용자 친화적인 인터페이스(UI)로 사용에 어색함 또는 불편함이 없는가?
- [ ]  API 문서에 모든 기능을 포함하고, Request/Response 에 대해 명확히 설명하였는가?
- [ ]  데이터베이스 테이블 간 연관관계를 적절히 설정하였는가?

[기능]

- [ ]  Spring Security 의 인증/인가를 활용하여 회원가입/로그인 기능을 구현하였는가?
- [ ]  프론트엔드에서 필요한 유효성 검사가 1 개 이상 고려되었는가?
- [ ]  이메일, 비밀번호, 이름을 포함한 정보로 회원 가입 기능이 구현되었는가?
- [ ]  이메일과 비밀번호로 로그인 기능이 구현되었는가?
- [ ]  숙박 상품 전체 조회 기능이 정상적으로 구현되었는가?
- [ ]  숙박 상품 개별 조회 기능이 정상적으로 구현되었는가?
- [ ]  장바구니(담기, 보기) 기능이 정상적으로 구동되는가?
- [ ]  주문하기 기능이 정상적으로 구동되는가?
- [ ]  결제하기 기능이 정상적으로 구동되는가?
- [ ]  결제하기가 성공할 경우, 주문 결과를 확인할 수 있는가?

[프로젝트 완성도]

- [ ]  Open API 의 데이터를 검증하고 적절하게 활용하였는가?
- [ ]  화면 컴포넌트 단위는 적절한가?
- [ ]  코드 품질과 안정성을 고려하여 테스트 케이스를 작성하고 테스트를 수행했는가?

[협업 및 커뮤니케이션]

- [ ]  협업 도구와 팀원들 간의 원활한 커뮤니케이션을 얼마나 잘 이끌어 나갔는가?
- [ ]  팀원들과의 원활한 커뮤니케이션과 정보 공유를 수행했는가?
  </details>

</details>

<br/>

## 🧑🏻‍💻 프로젝트 소개

Front-End와 Back-End와의 협업을 통해 완성한 숙박 예약 API 서비스 `Yanolja Clone` 프로젝트입니다.

## 📄 리팩토링 내용

인증/회원
- JWT Token  전달 방식 변경
  - 기존 헤더의 accesstoken과  refrehtoken을 모두 전달받는 형식에서  accesstoken만 전달 받는 방식으로 변경 (refreshtoken은 accesstoken 만료 후 재발급 때만 전달 받는다.)
  - 기존 헤더명을 고려하지 않고  accesstoken라는 명칭으로 보내던 것을 표준화된 방식으로 변경 ( **`Authorization`** 헤더와 Bearer 스킴)
- 인증/회원 로직 상 예외처리 안되어 있던 부분 추가 및 예외로직 통일화
- RequestDTO 에 Validation 처리
- 시큐리트 설정 코드 리펙토링 진행

결제 검증 API 추가
- 실제 결제 API를 사용해보는 데에는 어려움이 있었으나, PortOne 에서 제공해주는 API문서를 참고해서 실제 API를 사용할때와 비슷한 서비스 로직으로 수정


## 🧑🏻‍💻 Contributor 
### FE
> @[신현진](https://github.com/xxxjinn) : 개별 상품 조회, 개별 상품 상세 (accommodation, cart)  
> @[남현준](https://github.com/applevalley) : 검색, 전체 상품 조회, 카테고리 별 상품 조회 (accommodation, category, region)  
> @[박은영](https://github.com/SKY-PEY) : 메인, 헤더, 네비바 (accommodation, category, region)  
> @[이연수](https://github.com/suehub) : 로그인, 회원가입, 마이페이지 (auth, user)  
> @[최지훈](https://github.com/JitHoon) : 장바구니, 주문하기, 주문 결과 확인, 주문 내역 확인 (cart, reservation)

### BE
> @[김동준](https://github.com/Kim-Dong-Jun99) : 숙박 상품, 카테고리, 지역, 행사 API     
> @[김종훈](https://github.com/whdgns5059) : 장바구니, 결제, 주문 내역 API     
> @[노재혁](https://github.com/NoJaeHyuk) : 회원 인증, 회원 관련 API

## 📚 Stack
<br/>
<div algin = left>
  <img src="https://img.shields.io/badge/java-FF5A00?style=for-the-badge&logo=Java&logoColor=white">
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=springboot&logoColor=black">
  <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=black">
  <br/>
  <img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black">

  <br/>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/docker compose-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=black">
  <img src="https://img.shields.io/badge/aws ec2-232F3E?style=for-the-badge&logo=amazonec2&logoColor=white">
  <img src="https://img.shields.io/badge/aws s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">
  <img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
  <img src="https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
  <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=black">
</div>



## 🧑🏻‍💻 주요 기능 구현

## 🧑🏻‍💻 Script

### Run server application command

```
$ docker run -d -p 8080:8080 --name spring_server rlaehdwns99/ybe_mini_project
서버 설정 파일은 github action CI/CD 프로세스에서 설정해주는 관계로, 서버 프로그램은 도커 이미지로 실행해보실 수 있습니다.
```

## 🧑🏻‍💻 팀 소개

<table>
  <tr>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/102955516?v=4" alt="신현진 프로필" />
    </td>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/62874043?v=4" alt="남현준 프로필" />
    </td>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/139188760?v=4" alt="박은영 프로필" />
    </td>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/111065848?v=4" alt="이연수 프로필" />
    </td>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/101972330?v=4" alt="최지훈 프로필" />
    </td>
  </tr>
  <tr> 
    <td align="center">
      <a href="https://github.com/xxxjinn" target="_blank">
        신현진<br />
        Frontend
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/applevalley" target="_blank">
        남현준<br />
        Frontend
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/SKY-PEY" target="_blank">
        박은영<br />
        Frontend
      </a>
    </td>    
    <td align="center">
      <a href="https://github.com/suehub" target="_blank">
        이연수<br />
        Frontend
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/JitHoon" target="_blank">
        최지훈<br />
        Frontend
      </a>
    </td>
  </tr>
</table>

<table>
  <tr>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/95599193?v=4" alt="김동준 프로필" />
    </td>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/40512982?v=4" alt="김종훈 프로필" />
    </td>
    <td align="center" width="150px">
      <img src="https://avatars.githubusercontent.com/u/32382839?v=4" alt="노재혁 프로필" />
  </tr>
  <tr> 
    <td align="center">
      <a href="https://github.com/Kim-Dong-Jun99" target="_blank">
        김동준<br />
        BackEnd
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/whdgns5059" target="_blank">
        김종훈<br />
        BackEnd
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/NoJaeHyuk" target="_blank">
        노재혁<br />
        BackEnd
      </a>
    </td>
  </tr>
</table>

## 🧑🏻‍💻 Convention
### 패키지 구조
```
.
├── main
│   ├── java
│   │   └── com
│   │       └── ybe
│   │           └── mp10
│   │               ├── Mp10Application.java
│   │               ├── config
│   │               │   ├── JpaConfiguration.java
│   │               │   ├── OpenApiConfig.java
│   │               │   ├── S3Config.java
│   │               │   ├── SwaggerConfig.java
│   │               │   └── WebConfig.java
│   │               ├── domain
│   │               │   ├── accommodation
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   ├── auth
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── entity
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   ├── cart
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   ├── category
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   ├── festival
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   ├── open_api
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   └── service
│   │               │   ├── payment
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   ├── region
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   ├── roomoption
│   │               │   │   ├── controller
│   │               │   │   ├── dto
│   │               │   │   ├── exception
│   │               │   │   ├── model
│   │               │   │   ├── repository
│   │               │   │   └── service
│   │               │   └── user
│   │               │       ├── controller
│   │               │       ├── dto
│   │               │       ├── exception
│   │               │       ├── model
│   │               │       ├── repository
│   │               │       └── service
│   │               └── global
│   │                   ├── common
│   │                   │   ├── BaseTimeEntity.java
│   │                   │   ├── ReservationInfo.java
│   │                   │   ├── annotation
│   │                   │   ├── constant
│   │                   │   └── enums
│   │                   ├── component
│   │                   │   ├── OpenApiComponent.java
│   │                   │   └── S3ImageComponent.java
│   │                   ├── converter
│   │                   │   └── JsonConverter.java
│   │                   ├── exception
│   │                   │   └── GlobalException.java
│   │                   ├── handler
│   │                   │   ├── GlobalLocalExceptionHandler.java
│   │                   │   ├── GlobalServerExceptionHandler.java
│   │                   │   └── GlobalTestExceptionHandler.java
│   │                   ├── response
│   │                   │   ├── ErrorResponse.java
│   │                   │   ├── GlobalDataResponse.java
│   │                   │   └── GlobalResponse.java
│   │                   ├── security
│   │                   │   ├── SpringSecurityConfig.java
│   │                   │   ├── exception
│   │                   │   ├── filter
│   │                   │   ├── handler
│   │                   │   ├── provider
│   │                   │   ├── service
│   │                   │   └── token
│   │                   ├── util
│   │                   │   ├── CookieUtil.java
│   │                   │   ├── MockUserFactory.java
│   │                   │   ├── StringUtil.java
│   │                   │   └── TimeUtil.java
│   │                   └── validator
│   │                       └── AccommodationValidator.java
│   └── resources
│       ├── application-local.yml
│       ├── application-server.yml
│       ├── application-test.yml
│       ├── application.yml
│       ├── static
│       └── templates
└── test
    └── java
        ├── integrate
        │   └── com
        │       └── ybe
        │           └── mp10
        │               └── domain
        │                   └── accommodation
        └── unit
            └── com
                └── ybe
                    └── mp10
                        └── domain
                            └── accommodation
```

### 📄ERD

![](https://ybe-bucket.s3.ap-northeast-2.amazonaws.com/img.png)

### Commit
- Feat: 기능 구현
- Fix: 수정
- Refactor: 개선
- Design: 스타일
- Command: 주석
- Doc: 문서, 이슈 템플릿
- Chore: 코드에 영향을 주지 않는 작업들
- Delete: 파일 삭제
- Test: 테스트 관련 작업들

### Issues, PR, Merge
1. 이슈를 우선 적으로 생성합니다.
2. 생성한 이슈에 대해서 개발을 한 후에, 이슈를 닫는 pull-request를 develop 브랜치로 생성합니다.
3. pull-request 생성시, 실행되는 ci github action이 문제 없이 종료되면 merge 합니다.

### Branch 전략
- main, develop 브랜치를 생성하고, feature 별로 브랜치를 생성해서 작업을 진행했습니다.

### 프로젝트 회고

김동준
숙박 상품 재고 관리와 결제 방식을 프로젝트 초기부터 깊게 고민하지 않은 점이 아쉬웠다. 리팩토링 기간동안 숙박 상품 재고 관리에 대해서도 고민을 해보았지만, 마음에 드는 해결책을 찾지 못한 점도 아쉬움으로 남는 것 같다.

김종훈
프로젝트 리펙토링시에 엔티티 변경이 주는 영향력을 너무 얕봤던 것이 아쉬웠다. 단순이 엔티티 몇개 추가 제거가 아니라 연관 된 거의 모든 작업에 영향을 준다는걸 새삼 다시 느끼게 되었다. 이번프로젝트를 통해 아쉬웠던 점을 돌아보고 다음 프로젝트에서 도움이 되도록 하겠다.


노재혁
JWT 적용이 처음이다보니 프로젝트기간 동안 미처 고려하지 못한 부분들이 있었는데 그런 부분들에 대해 공부하고 적용할 수 있는 시간이었다.

그리고 코드 리펙토링 및 예외, Validation 처리 등 기본적으로 해야될 부분이 많이 생략되었는데 이번 기간 동안 해당 이슈를 처리하면서 얼마나 미흡하게 진행했는지를 알게되어 다음 프로젝트에 도움이 많이 될 것 같다.

## 🧑🏻‍💻 개발 기간 : `2주, 23.11.20 (월) ~ 23.12.01 (금)`
