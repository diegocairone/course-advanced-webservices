
-- create sample data to insert into employees table mapped by com.cairone.data.domain.Employee
-- with fields id, curp, name, familyName, birthDate considering the following rules:
-- - id is a UUID
-- - curp is a string with 18 characters
-- - name is a string with 50 characters and in uppercase
-- - familyName is a string with 50 characters and in uppercase
-- - birthDate is a date in the format yyyy-MM-dd

INSERT INTO employees (id, curp, name, family_name, birth_date, gender, created_by, created_on, updated_by, last_updated) VALUES
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'PEGJ850315HJCRRN07', 'JAMES', 'SMITH', '1985-01-01', 'H', '7757342b-ea0f-448d-bc15-25fa472467b3', '2024-02-20T10:00:00', '7757342b-ea0f-448d-bc15-25fa472467b3', '2024-02-20T10:00:00'),
('47290e4a-0cc0-4b66-bb54-be780ce43dac', 'PEGJ791115HJCRRN03', 'ANDREW', 'CARPENTER', '1979-11-15', 'H', '7757342b-ea0f-448d-bc15-25fa472467b3', '2024-02-20T10:00:00', '7757342b-ea0f-448d-bc15-25fa472467b3', '2024-02-20T10:00:00'),
('0ae3607c-8837-47ac-a6ad-7cc1ad7ec6a1', 'PEGJ850802HJCRRN03', 'ANDREW', 'CRUISE', '1985-08-02', 'H', '7757342b-ea0f-448d-bc15-25fa472467b3', '2024-02-20T10:00:00', '7757342b-ea0f-448d-bc15-25fa472467b3', '2024-02-20T10:00:00')
;
