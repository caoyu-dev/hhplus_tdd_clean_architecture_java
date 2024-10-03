# ERD 개요
1. Lecture (특강)
- lectureId (PK): 특강 ID (기본 키)
- title: 특강 제목
- lecturer: 강연자
- lectureDate: 특강 날짜
  
2. Application (신청)
- applicationId (PK): 신청 ID (기본 키)
- userId: 신청한 유저 ID
- lectureId (FK): 특강 ID (외래 키, Lecture 와의 관계)
- applicationTime: 신청 시간

# ERD 관계
Lecture 와 Application 은 1 대 N 관계 즉, 하나의 Lecture 에는 여러 명의 사용자가 신청할 수 있습니다. 
반면, 하나의 Application 은 하나의 Lecture 에 대한 신청을 의미합니다.

Application 엔티티는 신청한 유저의 정보와 함께, 어떤 특강에 대한 신청인지를 나타내기 위해 lectureId 를 외래 키로 참조하고 있습니다.

<img width="486" alt="스크린샷 2024-10-03 오후 11 10 52" src="https://github.com/user-attachments/assets/f61159c9-3be1-4cef-8147-56c78403945a">

설명
- Lecture: 특강 정보를 담고 있는 테이블 (lectureId: 기본 키)
- Application: 신청 정보를 담고 있는 테이블 (applicationId: 기본 키, lectureId 는 Lecture 테이블의 외래 키로 사용되어 어떤 특강에 대한 신청인지를 나타냄)
