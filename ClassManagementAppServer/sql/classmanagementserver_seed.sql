-- Plaintext passwords (testing only; DB stores bcrypt cost-12 hashes):
--   id 1  jc.uwimana@school.rw                 password = ck_ceb81f9d7914c120c8dcd19f3e351128
--   id 2  marie.mukamana@school.rw             password = ck_18d0752b1825bc5430beb45f683514f2
--   id 3  eric.niyonzima@school.rw             password = ck_6c6fa6115ab33edf6e595ed3a8b317fa
--   id 4  aline.ingabire@school.rw             password = ck_baa4b71add2467ac778eedb3693dffbc
--   id 5  patrick.habimana@school.rw           password = ck_fbf24050a748dbcfac619e630dde29a6

USE `classManagementAppServer`;

INSERT INTO `classManagementAppServer`.`user` (`id`, `username`, `password`) VALUES
  (1, 'jc.uwimana@school.rw', '$2a$12$FnBdTJTdXwXtrDSkNem2r.f3zbiUzDxSWS6TjnWofRqTnvQl1MnBO'),
  (2, 'marie.mukamana@school.rw', '$2a$12$PXBbHh0y20BQptt0RMaid.jJC71kppSyRsKVlWkMrj2PXdx2bt2dO'),
  (3, 'eric.niyonzima@school.rw', '$2a$12$QJuekU2qgn0apXkRWptFnucq60ByBAH26nNHJG7VCsX.uV8POToey'),
  (4, 'aline.ingabire@school.rw', '$2a$12$IfeCOr3F7a/Pa1l2Uzw3g.YAFXaaBoqAsDr9tAgq1mF5ugtpn9Uhy'),
  (5, 'patrick.habimana@school.rw', '$2a$12$KZBtykdt6Wh/bRpvWcaMG.sR0Hg252loPJWFy7DbgBWRymQBtxO.m');
  

INSERT INTO `classManagementAppServer`.`user_role`(`user_id`, `role_id`) VALUES
  (1, 1),
  (2, 1),
  (3, 1),
  (4, 1),
  (5, 1);

INSERT INTO `classManagementAppServer`.`user_token_type` (`user_id`, `token_type_id`) VALUES
  (1, 1),
  (2, 1),
  (3, 1),
  (4, 1),
  (5, 1);

INSERT INTO `classManagementAppServer`.`user_token_amount` (`user_id`, `token_amount`, `date`) VALUES
  (1, 0, '2026-10-01 08:00:00'),
  (2, 0, '2026-10-01 08:00:00'),
  (3, 0, '2026-10-01 08:00:00'),
  (4, 0, '2026-10-01 08:00:00'),
  (5, 0, '2026-10-01 08:00:00');


INSERT INTO `classManagementAppServer`.`user_schedule` (`id`, `schedule_uuid`, `user_id`, `title`, `description`, `date`) VALUES
  (1, '6f03675a-1600-435a-8999-50d836f675cc', 1, 'Term 3 exam supervision', 'Supervise the S4 MCB Mathematics end-of-term exam in Room 12.', '2026-10-15 08:00:00'),
  (2, '1738f7d9-3d9c-4724-91e2-0b8f6b0d549b', 2, 'Parents'' meeting - S4 PCB', 'Discuss Term 2 results and attendance with parents in the main hall.', '2026-10-17 14:00:00'),
  (3, 'd3ac94af-0f21-4db6-acad-4a268d116ece', 3, 'Marking Physics quizzes', 'Finish marking and entering Physics quiz scores for S4 MCB.', '2026-10-16 15:30:00'),
  (4, '39263059-f28c-405d-9fb1-7c2390c192cf', 4, 'Staff meeting', 'Monthly staff meeting with the headteacher.', '2026-10-20 10:00:00'),
  (5, 'f29d0da9-953f-48f1-a09f-76b5a170b338', 5, 'Umuganda community work', 'Community service day with students at the school compound.', '2026-10-31 07:30:00'),
  (6, '658cda14-95e6-4af5-93bd-04cf0fd630f1', 1, 'Science fair preparation', 'Prepare student projects for the district science fair in Huye.', '2026-10-22 13:00:00'),
  (7, '0becd7b0-3898-4190-b9eb-dacc0cb1e29c', 2, 'Revision class - S5 MEG', 'Extra Economics revision session before national mock exams.', '2026-10-24 16:00:00'),
  (8, '4a23d596-2217-4ead-9bc4-96cb8e81973e', 3, 'Lesson plan submission', 'Submit Term 1 2027 lesson plans to the Director of Studies.', '2026-11-02 09:00:00'),
  (9, '1e27a1c0-8a6a-43ec-a4ed-e6a46b4cb242', 4, 'School trip to Nyungwe', 'Educational trip for S5 Geography students.', '2026-11-07 06:30:00'),
  (10, 'd0eda82f-8f6d-4558-8ef8-aa3892276658', 5, 'Report cards distribution', 'Hand out Term 3 report cards and hold class feedback.', '2026-11-27 11:00:00'),
  (11,'d0eda82f-8f6d-4558-8ef8-aa3892276657', 2, 'Term 2 marks submission', 'Submitted Term 2 marks to the Director of Studies.', '2026-09-18 09:00:00'),
  (12,'d0eda82f-8f6d-4558-8ef8-aa3892276656', 3, 'Attendance audit','Reviewed Term 2 attendance registers.', '2026-09-18 09:00:00'),
  (13,'d0eda82f-8f6d-4558-8ef8-aa3892276655', 4, 'Quiz 1 marking', 'Marked Economics Quiz 1 for S4 MEG.', '2026-09-18 09:00:00'),
  (14,'d0eda82f-8f6d-4558-8ef8-aa3892276654', 5, 'Mid-term exam supervision', 'Supervised the S4 HEG mid-term exam.', '2026-09-18 09:00:00'),
  (15,'d0eda82f-8f6d-4558-8ef8-aa3892276653', 5, 'Science club meeting', 'Weekly meeting with the science club.', '2026-09-18 09:00:00');

DELETE FROM `classManagementAppServer`.`user_schedule` WHERE id IN (11,12,13,14,15);  -- this will insert the schedules inside archive_schedule table

INSERT INTO `classManagementAppServer`.`chat_rating` (`id`, `user_message`, `bot_message`, `rate`, `dateTime`) VALUES
  (1, 'Can you suggest a lesson plan on quadratic equations for S4?', 'Sure! Start with a 10-minute recap of factorisation, then introduce the quadratic formula with two worked examples.', 4.5, '2026-09-02 09:11:00'),
  (2, 'Which students scored below 50% in the Mathematics quiz?', 'Based on your records, Mutesi Claudine and Nshuti Olivier scored below 50%.', 5.0, '2026-09-04 09:13:00'),
  (3, 'Show me this week''s attendance for Physics.', 'Attendance for Physics this week was 5 out of 6 students on average.', 4.0, '2026-09-06 09:15:00'),
  (4, 'Give me five quick quiz questions on Economics.', 'Here are five questions covering demand, supply, and elasticity...', 3.5, '2026-09-08 09:17:00');

INSERT INTO `classManagementAppServer`.`user_feedback` (`id`, `username`, `feedback`, `timestamp`) VALUES
  (1, 'jc.uwimana@school.rw', 'The attendance screen is very easy to use. Murakoze!', '2026-09-20 18:05:00'),
  (2, 'marie.mukamana@school.rw', 'Please add an option to export marks to Excel.', '2026-09-22 12:40:00'),
  (3, 'eric.niyonzima@school.rw', 'The chatbot gives helpful quiz ideas for my Economics classes.', '2026-09-25 16:10:00'),
  (4, 'aline.ingabire@school.rw', 'I would like a Kinyarwanda language option in the app.', '2026-09-30 08:55:00'),
  (5, 'patrick.habimana@school.rw', 'The paid plan is worth it, but I wish the tokens lasted longer.', '2026-10-03 19:20:00'),
  (6, 'solange.uwitonze@school.rw', 'Sometimes the app is slow when the internet is weak.', '2026-10-05 07:45:00'),
  (7, 'emmanuel.nzeyimana@school.rw', 'Great app for managing my classes and assessments.', '2026-10-08 13:30:00');
