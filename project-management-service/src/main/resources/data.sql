
-- create sample data to insert into employees table mapped by com.cairone.data.domain.Employee
-- with fields id, curp, name, familyName, birthDate considering the following rules:
-- - id is a UUID
-- - curp is a string with 18 characters
-- - name is a string with 50 characters and in uppercase
-- - familyName is a string with 50 characters and in uppercase
-- - birthDate is a date in the format yyyy-MM-dd

INSERT INTO employees (id, curp, name, family_name, birth_date, gender) VALUES
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'PEGJ850315HJCRRN07', 'JAMES', 'SMITH', '1985-01-01', 'M'),
('f47ac10b-58cc-4372-a567-0e02b2c3d480', 'PEGJ850315HJCRRN08', 'JOHN', 'DOE', '1985-01-02', 'M'),
('f47ac10b-58cc-4372-a567-0e02b2c3d481', 'PEGJ850315MJCRRN09', 'JANE', 'SMITH', '1985-01-03', 'F'),
('f47ac10b-58cc-4372-a567-0e02b2c3d482', 'PEGJ850315HJCRRN10', 'JACK', 'DOE', '1985-01-04', 'M'),
('f47ac10b-58cc-4372-a567-0e02b2c3d483', 'PEGJ850315HJCRRN11', 'JILL', 'DOE', '1985-01-05', 'M'),
('f47ac10b-58cc-4372-a567-0e02b2c3d484', 'PEGJ850315HJCRRN12', 'JAMES', 'SMITH', '1985-01-06', 'M'),
('f47ac10b-58cc-4372-a567-0e02b2c3d485', 'PEGJ850315HJCRRN13', 'JOHN', 'DOE', '1985-01-07', 'M'),
('f47ac10b-58cc-4372-a567-0e02b2c3d486', 'PEGJ850315MJCRRN14', 'JANE', 'DOE', '1985-01-08', 'F'),
('f47ac10b-58cc-4372-a567-0e02b2c3d487', 'PEGJ850315HJCRRN15', 'JACK', 'DOE', '1985-01-09', 'M'),
('f47ac10b-58cc-4372-a567-0e02b2c3d488', 'PEGJ850315HJCRRN16', 'JILL', 'DOE', '1985-01-10', 'M')
;
