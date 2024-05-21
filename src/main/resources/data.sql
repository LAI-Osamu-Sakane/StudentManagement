INSERT INTO students (name, kana, nickname, mail, area, age, sex, remark)
VALUES( '坂根 治', 'サカネ オサム', 'オサム', 'aa.bb@zzz.com', '関西', 52, '男性', 'とりあえず'),
('鈴木 祐輔', 'スズキ ユウスケ', 'ユウスケ', 'cc.dd@zzz.com', '関東', 40, '男性', 'とりあえず'),
('安積 弘行', 'アヅミ ヒロユキ', 'ヒロユキ', 'ee.ff@zzz.com', '関西', 35, '男性', 'とりあえず')
;

INSERT INTO students_courses ( studentId, course_name, starting_date, scheduled_end_date)
VALUES(1, 'Java Basic', '2024-04-25', '2025-04-24'),
(1, 'Google Apps Script Basic', '2023-04-25', '2024-04-24'),
(2, 'JavaStandard', '2024-03-25', '2025-03-24'),
(3, 'Python Basic', '2024-05-25', '2025-05-24')
;