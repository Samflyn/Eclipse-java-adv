/*Data Definition Language are used to define database object
All DDL commands are auto-comitted*/
/*Create*/
create table my_table (
    empno number(10),
    empname varchar2(20),
    sal number(8, 2)
);

/*Alter*/
/* Add */
alter table
    my_table
add
    deptno number (10);

alter table
    my_table
add
    (deptno number(10), comm number(5));

/* Modify */
alter table
    my_table
modify
    (empno number(7), empname varchar2(30));

/* Renaming Column */
alter table
    my_table rename empname to ename;

/* Drop */
alter table
    my_table drop Column empname;

alter table
    my_table drop (empname, empno, sal);

/*to get the list of all tables in current user*/
select
    *
from
    tab;

/*Data Query Language (DQL)*/
select
    *
from
    my_table;

select
    empno,
    empname,
    sal
from
    my_table;

select
    empno as EMPNO,
    empname as NAME,
    sal as SALARY
from
    my_table;

/*Data Manipulation Language(DML)*/
/*All DML commands are not auto-comitted*/
insert into
    my_table
values(1000, 'sam', 'developer', 8888, '21-01-1989', 90);

/* to insert at specific column */
create table my_table (
    empno number(10),
    empname varchar2(30),
    sal number (10, 2),
    deptno number(4)
);

insert into
    my_table (empno, empname)
values
    (1000, 'sam');

/*rest would be null*/
/*OR*/
insert into
    my_table
values(1000, 'sam', NULL, NULL);

/*inserting dynamically*/
insert into
    my_table (& eno, '&ename', & sal, & depno);

/* Delete */
/* Truncate */
/* Table level DDL */
truncate table my_table;

/* Update */
update
    my_table
set
    sal = 88888;

/* Oracle Clauses */
select
    *
from
    my_table
where
    ename = 'SAM';

select
    *
from
    my_table
where
    sal IS NOT NULL;

update
    my_table
set
    sal = 1111
where
    ename = 'SAM';

delete from
    my_table
where
    ename = 'SAM';

/* Special operators in Oracle */
/* IN - list of values */
select
    *
from
    my_table
where
    deptno in (10, 20, 30, 40);

select
    *
from
    my_table
where
    deptname in ('DEVELOPER', 'MANAGER');

/* NOT IN */
select
    *
from
    my_table
where
    deptno not in (10, 20, 30, 40);

select
    *
from
    my_table
where
    deptname not in ('DEVELOPER');

/* BETWEEN */
select
    *
from
    my_table
where
    empno between 1000
    and 2000;

/* NOT BETWEEN */
select
    *
from
    my_table
where
    empno not between 1000
    and 2000;

/* LIKE - % for zero or more no. of chars |  _ for single char */
select
    *
from
    my_table
where
    empname like 'S%';

select
    *
from
    my_table
where
    empname like 'SA_';

/* IS NULL , NULL */
/* Order by Clause */
select
    *
from
    my_table
order by
    sal;

select
    *
from
    my_table
order by
    sal asc;

select
    *
from
    my_table
order by
    sal desc;

/* order and where */
select
    *
from
    my_table
where
    deptname = 'DEVELOPER'
order by
    sal;

/* FUNCTIONS */
/* Single Row Functions */
/* Numeric , abs returns +ve value*/
select
    abs(-10)
from
    dual;

select
    power(5, 2)
from
    dual;

select
    sqrt(25)
from
    dual;

select
    sin(30)
from
    dual;

select
    cos(30)
from
    dual;

select
    exp(10)
from
    dual;

select
    mod(10, 3)
from
    dual;

/* value rounded to next integer */
select
    ceil(10.6)
from
    dual;

/* value rounded to previous integer */
select
    floor(10.6)
from
    dual;

/* rounded to nearest integer */
select
    round(10.695, 2)
from
    dual;

select
    trunc(10.12345, 2)
from
    dual;

/* Character Functions */
select
    lower('SAM')
from
    dual;

select
    upper('sam')
from
    dual;

select
    initcap('sam flynn')
from
    dual;

select
    length('sam')
from
    dual;

select
    ascii('s')
from
    dual;

select
    ltrim('abcxyz', 'abc')
from
    dual;

select
    rtrim('abcxyz', 'xyz')
from
    dual;

select
    trim(' abc ')
from
    dual;

/* to replace single char */
select
    translate('aaaaaaaaebre', 'a', 'z')
from
    dual;

select
    translate('abcabc', 'abc', 'xyz')
from
    dual;

select
    replace('abcz', 'abc', 'wxy')
from
    dual;

select
    substr('abcdef', 4)
from
    dual;

select
    substr('abcdef', 3, 4)
from
    dual;

/* to fill empty space in output */
select
    lpad(1234, 7, '#')
from
    dual;

select
    rpad(1234, 7, '#')
from
    dual;

/* Date Function */
select
    months_between('01-jan-2000', '29-feb-2000')
from
    dual;

select
    add_months('01-jan-2000', -5)
from
    dual;

select
    next_day('01-jan-2000', 'fri')
from
    dual;

select
    last_day('01-jan-2000')
from
    dual;

select
    to_char(sysdate, 'dd-mon-yyyy')
from
    dual;

select
    to_date('01-jan-2000', 'dd-mon-yyyy')
from
    dual;

/* Misc */
select
    user
from
    dual;

select
    uid
from
    dual;

select
    least(1, 2, 3, 4)
from
    dual;

select
    least('a', 'g', 's', 'b')
from
    dual;

select
    least('A', 50, 'Z', 'B')
from
    dual;

/* NVL will replace null with value */
select
    empname,
    empno,
    sal,
    nvl(comm, 500)
from
    my_table;

/* NVL2 will retuen exp1 if column value is not null else returns exp2 */
select
    nvl2(comm, sal + comm, sal)
from
    my_table;

/* NULLIF returns null if both exps are same else returns exp1 */
select
    nullif(10 + 2, 14 -2)
from
    dual;

/* COALESCE returns first null value */
select
    coalesce(
        10 * null,
        10 + null;

10,
10 / null
)
from
    dual;

/* Group */
/* Group Functions */
select
    count(*)
from
    my_table;

select
    count(empno)
from
    my_table;

select
    sum(empno)
from
    my_table;