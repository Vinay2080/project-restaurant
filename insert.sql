-- Insert 5 new users
INSERT INTO users (id, first_name, last_name, email, password, phone_number, date_of_birth, created_date, last_modified_date, is_enabled, is_account_locked, is_credential_expired, is_email_verified, is_phone_verified)
VALUES
    ('a1b2c3d4-e5f6-7890-1234-567890abcdef', 'Alex', 'Turner', 'alex.t@restaurant.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqO0xpyN0wC4FPF9aXrKks3sKdJQ1C', '+1234567810', '1990-05-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, false, true, true),
    ('b2c3d4e5-f6a7-8901-2345-67890abcdef1', 'Sophia', 'Chen', 'sophia.c@restaurant.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqO0xpyN0wC4FPF9aXrKks3sKdJQ1C', '+1234567811', '1991-08-22', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, false, true, true),
    ('c3d4e5f6-a7b8-9012-3456-7890abcdef12', 'Ryan', 'Patel', 'ryan.p@restaurant.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqO0xpyN0wC4FPF9aXrKks3sKdJQ1C', '+1234567812', '1989-03-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, false, true, true),
    ('d4e5f6a7-b8c9-0123-4567-8901abcdef12', 'Emma', 'Garcia', 'emma.g@restaurant.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqO0xpyN0wC4FPF9aXrKks3sKdJQ1C', '+1234567813', '1992-11-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, false, true, true),
    ('e5f6a7b8-c9d0-1234-5678-9012abcdef12', 'James', 'Wilson', 'james.w@restaurant.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqO0xpyN0wC4FPF9aXrKks3sKdJQ1C', '+1234567814', '1993-07-18', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, false, true, true);

-- Assign ROLE_STAFF to all new users
INSERT INTO user_roles (users_id, roles_id)
SELECT id, (SELECT id FROM roles WHERE name = 'ROLE_STAFF')
FROM users
WHERE id IN (
             'a1b2c3d4-e5f6-7890-1234-567890abcdef',
             'b2c3d4e5-f6a7-8901-2345-67890abcdef1',
             'c3d4e5f6-a7b8-9012-3456-7890abcdef12',
             'd4e5f6a7-b8c9-0123-4567-8901abcdef12',
             'e5f6a7b8-c9d0-1234-5678-9012abcdef12'
    );

-- Create staff records for the new users
-- Create staff records for the new users
-- INSERT INTO staff (id, user_id, employee_id, position, hire_date, employment_type, hourly_rate, monthly_salary, is_active, created_at, updated_at, created_date, last_modified_date)
-- VALUES
--     ('f6a7b8c9-d0e1-2345-6789-0123abcdef12', 'a1b2c3d4-e5f6-7890-1234-567890abcdef', 'EMP-015', 'Sous Chef', '2023-01-10', 'FULL_TIME', NULL, 5200.00, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--     ('07b8c9d0-e1f2-3456-7890-1234abcdef12', 'b2c3d4e5-f6a7-8901-2345-67890abcdef1', 'EMP-016', 'Server', '2023-02-15', 'PART_TIME', 17.00, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--     ('18c9d0e1-f2a3-4567-8901-2345abcdef12', 'c3d4e5f6-a7b8-9012-3456-7890abcdef12', 'EMP-017', 'Line Cook', '2023-03-20', 'FULL_TIME', NULL, 3400.00, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--     ('29d0e1f2-a3b4-5678-9012-3456abcdef12', 'd4e5f6a7-b8c9-0123-4567-8901abcdef12', 'EMP-018', 'Hostess', '2023-04-25', 'PART_TIME', 15.50, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--     ('3ae1f2b3-c4d5-6789-0123-4567abcdef12', 'e5f6a7b8-c9d0-1234-5678-9012abcdef12', 'EMP-019', 'Bartender', '2023-05-30', 'PART_TIME', 18.00, NULL, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);