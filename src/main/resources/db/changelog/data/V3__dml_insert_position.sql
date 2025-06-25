INSERT INTO t1_position (id, code, name, is_delete)
SELECT 1, 'SA', 'System Analyst', 0
    WHERE NOT EXISTS (
    SELECT 1 FROM t1_position WHERE id = 1 OR code = 'SA'
);

INSERT INTO t1_position (id, code, name, is_delete)
SELECT 2, 'BPS', 'BPS', 0
    WHERE NOT EXISTS (
    SELECT 1 FROM t1_position WHERE id = 2 OR code = 'BPS'
);

INSERT INTO t1_position (id, code, name, is_delete)
SELECT 3, 'PRG', 'Programmer', 0
    WHERE NOT EXISTS (
    SELECT 1 FROM t1_position WHERE id = 3 OR code = 'PRG'
);

INSERT INTO t1_position (id, code, name, is_delete)
SELECT 4, 'TEST', 'Tester', 0
    WHERE NOT EXISTS (
    SELECT 1 FROM t1_position WHERE id = 4 OR code = 'TEST'
);

INSERT INTO t1_position (id, code, name, is_delete)
SELECT 5, 'HELP', 'Helpdesk', 0
    WHERE NOT EXISTS (
    SELECT 1 FROM t1_position WHERE id = 5 OR code = 'HELP'
);