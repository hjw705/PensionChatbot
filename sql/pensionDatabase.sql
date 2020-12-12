CREATE DATABASE pension;
SHOW DATABASES;
USE pension;

CREATE TABLE product (
	fin_co_no varchar(8),
    kor_co_nm varchar(30),
    fin_prdt_cd varchar(20) PRIMARY KEY,
    fin_prdt_nm varchar(50),
    prdt_type_nm varchar(10),
    avg_prft_rate decimal(6,2),
    dcls_rate decimal(6,2),
    btrm_prft_rate_1 decimal(6,2),
    btrm_prft_rate_2 decimal(6,2),
    btrm_prft_rate_3 decimal(6,2)
);
DESC product;
/*
fin_co_no 금융회사코드 - 0010170
kor_co_nm 금융회사 명 - 하나유비에스자산운용
fin_prdt_cd 금융상품코드 - KR5102314204
fin_prdt_nm 금융상품명 - 하나UBS인Best연금증권투자신탁(제1호)[채권]
prdt_type_nm 상품유형명 - 채권형
avg_prft_rate 연평균 수익률 [소수점 2자리] - 4.05
dcls_rate 공시이율 [소수점 2자리](현재 null) - null
btrm_prft_rate_1 과거 수익률1(전년도) [소수점 2자리] - 2.96
btrm_prft_rate_2 과거 수익률2(전전년도) [소수점 2자리] - 1.97
btrm_prft_rate_3 과거 수익률3(전전전년도) [소수점 2자리] - 3.35

하나의 상품에 여러개 존재
 - pnsn_recp_trm_nm 
 - mon_paym_atm_nm 
 - pnsn_strt_age_nm 
 - pnsn_recp_amt 
*/

CREATE TABLE pin_account (
	finacno int PRIMARY KEY
);

CREATE TABLE balance (
	finacno int PRIMARY KEY,
    bal int
);

SELECT * FROM product;