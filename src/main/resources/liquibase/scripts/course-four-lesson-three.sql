-- liquibase formatted sql

-- changeset rzelenin:1
create index student_name_index on студент (name);

-- changeset rzelenin:2
create index faculty_nc_index on факультет (color, name);