##  REST API 

**REST API**란 말을 개발 공부하면서도 많이 들었고, 구인글에서도 REST API를 구현할 줄 아는 사람, 혹은 관심이 있는 사람들 채용한다는 글도 많이 봤다  어렴풋이 아는 것 같으면서도 모르겠는 이 **REST API**를 정리하려고 한다. 

## 먼저 용어에 대해서 간단하게 정리해보자

| 주제 | 설명 |
| --- | --- | 
| REST | 자원 + 자원에 대한 행위 + 자원에 대한 행위 내용  | 
| API(application programming interface)| 자바에서 integerface를 보면 이를 작업 명세서로 보고 구현한다. 사용하는 입장에서는 이걸 보고 조건에 맞는 값을 전달해서 사용한다. 따라서 API는 약속이다. | 
| RESTFul API| REST API를 잘 지키면 RESTFul API라고 한다.| 



## REST API LEVEL 설명
| LEVEL | 설명 |
| --- | --- |
| LEVEL0 | URI에 행위 들어감  |
| LEVEL1 | 리소스로 식별 |
| LEVEL2 | LEVEL1 + HTTP메서드 사용 |
| LEVEL3 | LEVEL2 + HATEOAS  |


### **REST의 특징**

1. Server-Client(서버-클라이언트 구조)
2. Stateless(무상태)
3. Cacheable(캐시 처리 가능)
4. Layered System(계층화)
5. Uniform Interface(인터페이스 일관성)


## REST API설계시 고려사항

1. 사용자 중심에 API 설계
2. HTTP 메서드 사용
3. 상태코드 사용  (무조건 200번 사용하지 말자)
4. URI에 위험한 정보 포함X
5. 제공하려는 데이터를 복수형태로 포함시키기를 권장
6. 동사형보다 명사형으로 표현하기
7. 일관된 엔드포인트를 사용하기 (put users/{id}, delete user/{id} 이런식으로 구분 )

### **REST API 설계 예시**

**1. URI는 동사보다는 명사를, 대문자보다는 소문자를 사용하여야 한다.**

> Bad Example http://whdud.com/Running/Good Example  http://whdud.com/run/
> 

**2. 마지막에 슬래시 (/)를 포함하지 않는다.**

> Bad Example http://whdud.com/test/  Good Example  http://whdud.com/test
> 

**3. 언더바 대신 하이폰을 사용한다.**

> Bad Example http://whdud.com/test_blogGood Example  http://whdud.com/test-blog
> 

**4. 파일확장자는 URI에 포함하지 않는다.**

> Bad Example http://whdud.com/photo.jpg  Good Example  http://whdud.com/photo
> 

**5. 행위를 포함하지 않는다.**

> Bad Example http://whdud.com/delete-post/1  Good Example  http://whdud.com/post/1
>

##  RESTFul API를 구현하기 위해서 알아야 할 것 

RESTFUL이란 REST의 원리를 따르는 시스템을 의미합니다. 하지만 REST를 사용했다 하여 모두가 RESTful 한 것은 아닙니다.  REST API의 설계 규칙을 올바르게 지킨 시스템을 RESTful하다 말할 수 있으며

모든 CRUD 기능을 POST로 처리 하는 API 혹은 **URI 규칙을 올바르게 지키지 않은 API는** REST API의 설계 규칙을 올바르게 지키지 못한 시스템은 **REST API를 사용하였지만 RESTful 하지 못한 시스템이라고 할 수 있습니다.**


<br>   



공부한 자료 <br>
https://m.yes24.com/Goods/Detail/17942917 
 https://www.inflearn.com/course/spring-boot-restful-web-services 
https://appmaster.io/ko/blog/restful-apiyi-ijeomeun-mueosibnigga
https://khj93.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-REST-API%EB%9E%80-REST-RESTful%EC%9D%B4%EB%9E%80)
