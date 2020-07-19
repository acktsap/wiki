# Java Inside

## Summary

dcinside에는 비공식적인 api들이 있다. 들어가서 살펴보면 다 ${language}inside 로 이름이 지어져 있다.

kotlin이 이미 있으나 java 생태계와의 원활한 호환성을 위해서는 아직은 java로 작성하는 것이 낫지 않나 싶다.

- GoInside (GoLang) : https://github.com/geeksbaek/goinside
- GoDC (GoLang) : https://github.com/dfkdream/godc
- dcinside-python3-api (Python) : https://github.com/song9446/dcinside-python3-api
- KotlinInside (Kotlin) : https://github.com/organization/KotlinInside
- TypeInside (TypeScript) : https://github.com/Akachu/typeinside

## Decisions

- java 8
- dependencies
  - rest request
  - json parsing
- gradle 6.5.1
- naver에서 배운 library 설정 정리 및 적용
  - editorconfig
  - style directory
- 기간 : ~7월 말

## Progress

[javainside project](https://github.com/acktsap/javainside/projects/1)

## Features

- 주체
  - 유동닉
  - 고정닉
- 갤러리
  - 모든 갤러리 정보 조회
  - 모든 마이너 갤러리 정보 조회
- 글
  - 작성
  - 열람
  - 수정
  - 삭제
  - 추천
  - 비추천
- 댓글
  - 작성
  - 열람
  - 수정
  - 삭제

## References

unofficial dcinside apis : [https://gall.dcinside.com/mgallery/board/view/?id=api&no=3](https://gall.dcinside.com/mgallery/board/view/?id=api&no=3)