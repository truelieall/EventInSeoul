EventInSeoul
============

### 서울문화행사정보
서울에서 열리는 각종 문화행사정보 공유를 위한 웹 어플리케이션 입니다.
서울시에서 제공하는 정보를 활용하여 매일 매일 갱신되는 행사를 볼 수 있습니다.
현재 가장 Simple한 형태로 중요 기능만 구현된 상태입니다. (정보 수신/가공/저장/검색, 북마크 등)

### 사용된 기술정보

* JAVA - JDK 1.8
* Main Framework - Spring 4.3.7 (SpringBoot 1.5.2) 
* Dependency, Build - Maven
* Database - MYSQL
* ORM - JPA (Hibernate)
* 기타 - Ehcache, lombok, JUnit4(for test)
* WAS - Apache tmocat 8.x
* Frontend - Vue.js, Bootstrap, JQuery

### OpenAPI 정보 
- 서울열린데이터광장(서울문화행사정보) http://data.seoul.go.kr/

### 사용한 IDE 
- Eclipse

### Mysql DDL 
- (첨부예정)

### 기타 
- Spring profile은 dev로 초기 설정이 필요합니다. (spring.profiles.active=dev)
- application-dev.properties, api-dev.properties 확인 및 작성 후 기동가능합니다.
- 본 Repository는 (http://event.breakingthat.com) 자동 Build(Jenkins) 됩니다.