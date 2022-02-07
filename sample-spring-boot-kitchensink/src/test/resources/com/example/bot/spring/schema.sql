drop table if exists food;

create table if not exists FOOD (
	foodID bigint identity primary key,
	NAME varchar,
	category varchar,
	calories double,
	sodium double,
	saturatedFat double,
	protein double,
	carbohydrate double
);