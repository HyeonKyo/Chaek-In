# README

# 책크인 (CHAEK-IN)

## 0. 프로젝트 회고

---

### 1. 나의 역할
* AWS EC2에 Jenkins, Docker를 활용하여 REST API 서버와 추천 서버 CI/CD 환경 구축
* HTTPS, Nginx 등 인프라 설정
* 서비스 REST API 설계
* 빅데이터 추천을 제외한 모든 도메인 Service logic 구현
* Spring Security를 활용한 JWT 인증 로직 구현

### 2. 아쉬웠던 점
* 서비스 로직의 단위 테스트를 적용하지 못한 점
* API의 성능 측정을 해보지 못한 점
* 타이트한 일정으로 인해 알림 기능을 적용하지 못한 점

### 3. 느낀점

Jenkins와 Docker를 이용하여 CI / CD 환경을 구축하고, EC2를 관리하며 아키텍처 지식을 향상 시킬 수 있었다. 그리고 이전 프로젝트보다 로깅도 강화하고, JPA Batch Insert 문제 등을 겪으며 기술적으로 더 깊이를 가져갈 수 있었다. 
하지만 이런 저런 일정으로 인해 초기에 기획한 단위 테스트나 서버 성능 테스트 등을 수행하지 못해 아쉽고 다음 프로젝트에서는 반드시 성능을 체크하고 개선하며 서버 개발자로서 한 층 더 성장하고 싶다.

## 1. 서비스 소개

---

### 서비스 개요

- 진행 기간 : 2022.08.29~2022.10.07(6주)
- 한 줄 소개 : 책 속으로 여행이 더 설레게
- 서비스 명 : 책크인(CHAEK-IN)

### 기획 배경

- 내가 읽은 책을 쉽게 기록하고, 좋아할 만한 책을 추천 받고 그것을 바탕으로 다양한 사람들과 독서 모임을 할 수 있는 종합 독서 어플리케이션
    - 유저가 좋아할 만한 책을 유저의 책 선호 데이터를 기반으로 추천
    - 완독 모임, 유저와 비슷한 사람들의 모임 등 다양한 독서 모임 추천
    - 바코드 리더 기술과 ISBN을 활용한 독서 기록
    - OCR 기술을 활용한 간편한 책 속 문장 기록

### 서비스 화면

![회원 가입, 초기 정보 수집](README.assets/%25ED%259A%258C%25EC%259B%2590%25EA%25B0%2580%25EC%259E%2585_%25EC%25B4%2588%25EA%25B8%25B0%25EC%25A0%2595%25EB%25B3%25B4%25EC%2588%2598%25EC%25A7%2591.gif)

회원 가입, 초기 정보 수집

![홈 화면, 책 읽기 시작](README.assets/%25ED%2599%2588%25ED%2599%2594%25EB%25A9%25B4_%25EC%25B1%2585%25EC%259D%25BD%25EA%25B8%25B0.gif)

홈 화면, 책 읽기 시작

![책 읽기 끝, 책 상세, 리뷰, OCR 기록](README.assets/%25EC%25B1%2585%25EC%259D%25BD%25EA%25B8%25B0%25EB%2581%259D_%25EC%2583%2581%25EC%2584%25B8_%25EB%25A6%25AC%25EB%25B7%25B0_OCR.gif)

책 읽기 끝, 책 상세, 리뷰, OCR 기록

![책 추천 ](README.assets/%25EC%25B1%2585%25EC%25B6%2594%25EC%25B2%259C.gif)

책 추천 

![독서 모임 추천](README.assets/%25EB%25AA%25A8%25EC%259E%2584.gif)

독서 모임 추천

![마이페이지(책 달력, 읽고있는 책, 찜한 책, 읽은 책)](README.assets/%25EB%25A7%2588%25EC%259D%25B4%25ED%258E%2598%25EC%259D%25B4%25EC%25A7%2580.gif)

마이페이지(책 달력, 읽고있는 책, 찜한 책, 읽은 책)

### 팀원 소개

![Untitled](README.assets/Untitled.png)

### UCC

[https://youtu.be/tjm-BSuf-mc](https://youtu.be/tjm-BSuf-mc)

## 2. 기획

---

### ERD

![ERD.png](README.assets/ERD.png)

### Figma

[https://www.figma.com/file/UdFN1ZLH4m3ajfb3syvtnu/책크인?node-id=0%3A1](https://www.figma.com/file/UdFN1ZLH4m3ajfb3syvtnu/%EC%B1%85%ED%81%AC%EC%9D%B8?node-id=0%3A1)

![Untitled](README.assets/Untitled%201.png)

### API 명세

![Untitled](README.assets/Untitled%202.png)

## 3. 기술 스택

---

### Architecture

![Untitled](README.assets/Untitled%203.png)

### Frontend

![Untitled](README.assets/Untitled%204.png)

- React : 18.0.0
- React Native : 0.69.5

### Backend

![Untitled](README.assets/Untitled%205.png)

- Spring boot : 2.7.4
- QueryDSL : 1.0.10
- mariaDB : 10.6.8

### Data

![Untitled](README.assets/Untitled%206.png)

- FastAPI : 0.85.0

### Infra

![Untitled](README.assets/Untitled%207.png)

- docker : 20.10.18
- Jenkins : 2.60.3
- redis : 7.0.5

### IDE

- Intellij : 2022.01.03 (community)

## 4. 프로젝트 진행

---

### Git

- Git Flow를 브랜치 전략으로 선정
- Develop 브랜치와 Master 브랜치에 MR을 Merge하면, GitLab Webhook이 발생하고 Jenkins를 이용하여 자동 배포 환경 구현
- Develop 브랜치에 Merge되는 순간 Spring, FastAPI 코드로 Docker Image 생성
- Master 브랜치에 Merge되는 순간 Docker Image를 Run하여 자동 배포
- Git commit convention
    
    ```markdown
    ✨ feat : 기능 (새로운 기능)
    🐛 fix : 버그 (버그 수정)
    ♻ refactor : 리팩토링
    💄 style : 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
    📝 docs : 문서 (문서 추가, 수정, 삭제)
    ✅ test : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
    🔨 chore : 기타 변경사항 (빌드 스크립트 수정 등)
    ```
    

### Jira

- 개발 일정 관리

![Untitled](README.assets/Untitled%208.png)

![Untitled](README.assets/Untitled%209.png)

### Notion

[https://pear-alder-eb9.notion.site/87831935456e4da7a1d9b9e7a05c92cd](https://www.notion.so/87831935456e4da7a1d9b9e7a05c92cd)

![Untitled](README.assets/Untitled%2010.png)
