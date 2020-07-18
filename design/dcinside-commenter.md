# Dcinside Commenter

## Idea

특정 글만 필터링 해서 댓글을 남길 수 있어야 함.

## Requirements

- 정해진 갤러리에 대해 크롤링을 해서 타겟 게시물을 파악할 수 있어야 함
- 크롤링 타겟을 나눌 수 있어야 함 (파일로 설정)
  - 갤러리
  - 작성자
  - 패턴
    - 제목
    - 본문
    - 댓글
- 타겟에 대한 행동을 나눌 수 있어야 함
  - 댓글 달기
  - 글 작성하기
  - etc
- 행동에 대한 content를 유동적으로 바꿀 수 있어야 함
  - 파일
    - yaml
    - json
  - database
- dcinside에 요청을 할 수 있어야 함
  - [golang api](https://github.com/geeksbaek/goinside)
  - [kotlin api](https://github.com/organization/KotlinInside)
  - [https://gall.dcinside.com/mgallery/board/view/?id=api&no=3](https://gall.dcinside.com/mgallery/board/view/?id=api&no=3)

## Design

### Language

golang? kotlin? java? java할바야 kotlin 공부할 겸 kotlin으로 하는게 좋을거 같긴 한데..

golang도 공부해서 괜찮을거 같단 말이야. kotlin이야 어차피 회사에서 쓸일 많을테니가

golang으로 해보자 그럼..!

음.. 더 생각해 보니 java로 javainside 만들고 batch processing server를 kotlin으로 만드는게 좋을 듯.

golang은 내년쯤에나 공부해 보고 이번은 일단 kotlin하고 java를 더 파보자

kotlin으로 만든 다음에 dockernizing해서 config 받아서 설정되게 하자!

### Operation

scheculing은 무엇으로..? 

서버는 어디에?

### Abstraction

TBD
