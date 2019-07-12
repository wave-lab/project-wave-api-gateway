# project_wave_API_Gateway


![wave.png](https://github.com/wave-lab/project-wave-scheduler-server/blob/master/img/wave.png?raw=true)


* 2019 SOPT 24기 커버곡 플랫폼 'wave'
* 프로젝트 기간 : 2019년 6월 29일 ~ 2019년 7월 12일]
* **API** - (https://github.com/wave-lab/project-wave-server/wiki)


![architecture.png](https://github.com/wave-lab/project-wave-scheduler-server/blob/master/img/architecture.png?raw=true)



![erd.png](https://github.com/wave-lab/project-wave-scheduler-server/blob/master/img/erd.PNG?raw=true)



## 시작하기

모든 소스코드는 IntelliJ IDEA + Window10 + JAVA 8 환경에서 작성되었습니다.

이 프로젝트에서는 아래 같은 **MAVEN 의존성 프로젝트**가 포함되어있습니다. 

```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.9</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

    </dependencies>
```

## 실행하기

### window 10 환경 기준

- 8080 포트를 사용합니다.

- `jdk8` 과 `maven` 을 설치합니다.

- `JAVA_JOME` 환경변수 설정을 합니다.

- `Path`에 `maven` 환경변수 설정을 합니다.

- 내장 톰캣을 이용해 서버를 배포 합니다.

- `application.properties` 파일이 필요합니다.

- spring boot 앱 실행

  ```
  mvn spring-boot:run
  ```

- 중지하려면, 키보드에서 `Crtl + C`를 누릅니다.

### AWS EC2 Ubuntu 환경

* 8080 포트를 사용합니다.

- `jdk8` 과 `maven` 을 설치합니다.

- 백 그라운드 spring boot 앱 실행

- 내장 톰캣을 사용해 배포합니다.

- `application.properties` 파일이 필요합니다.

- spring boot 앱 실행

  ```
  nohup mvn spring-boot:run&
  ```

- 중지하려면,  `netstat -tnlp` 명령어를 통해 프로세스를 kill 하십시오.

## 사용된 도구

- [Spring-boot](https://projects.spring.io/spring-boot/) - Spring-boot 웹 프레임워크
- [Maven](https://maven.apache.org/) - 의존성 관리 프로그램
- [Tomcat](http://tomcat.apache.org/) - 웹 애플리케이션 서버
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE
- [AWS EC2](https://aws.amazon.com/ko/ec2/?sc_channel=PS&sc_campaign=acquisition_KR&sc_publisher=google&sc_medium=english_ec2_b&sc_content=ec2_e&sc_detail=aws%20ec2&sc_category=ec2&sc_segment=177228231544&sc_matchtype=e&sc_country=KR&s_kwcid=AL!4422!3!177228231544!e!!g!!aws%20ec2&ef_id=WkRozwAAAnO-lPWy:20180412120123:s) - 클라우드 환경 컴퓨팅 시스템

## 개발자

- **김예진** - [jineeee](https://github.com/jineeee) 
- **심정욱** - [SimJungUk](https://github.com/SimJungUk) 
- **석영현** - [yeonghyeonSeok](https://github.com/yeonghyeonSeok) 

[기여자 목록](https://github.com/wave-lab/project-wave-server/graphs/contributors)을 확인하여 이 프로젝트에 참가하신 분들을 보실 수 있습니다.

## wave의 다른 프로젝트

- [ANDROID](https://github.com/wave-lab/project-wave-Android) 
- [IOS](https://github.com/wave-lab/project-wave-iOS) 
