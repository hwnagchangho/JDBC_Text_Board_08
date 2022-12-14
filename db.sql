DROP DATABASE IF EXISTS text_board1;
CREATE DATABASE text_board1;
USE text_board1;

CREATE TABLE article(
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
`body` TEXT NOT NULL
);

CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(20) NOT NULL,
	loginPw CHAR(200) NOT NULL,
	`name` CHAR(200) NOT NULL
);

#임시회원
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '홍길동';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '홍길순';

#게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;