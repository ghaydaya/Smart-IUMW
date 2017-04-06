-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 05, 2017 at 08:56 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `testdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `semester`
--

CREATE TABLE `semester` (
  `sem_number` varchar(20) NOT NULL,
  `sem_session` varchar(20) NOT NULL,
  `sem_code` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `semester`
--

INSERT INTO `semester` (`sem_number`, `sem_session`, `sem_code`) VALUES
('1', '2017', '12017'),
('2', '2017', '22017'),
('3', '2017', '32017'),
('4', '2017', '42017');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `student_name` varchar(100) NOT NULL,
  `passport` varchar(100) NOT NULL,
  `student_id` varchar(100) NOT NULL,
  `home_no` varchar(100) NOT NULL,
  `mobile_no` varchar(100) NOT NULL,
  `semester_enroll` varchar(100) NOT NULL,
  `intake` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_name`, `passport`, `student_id`, `home_no`, `mobile_no`, `semester_enroll`, `intake`, `password`) VALUES
('N', 'P2013', 'ID', '0168', '017', '4', '2013', '123'),
('Name', 'Passport No', 'Student ID', 'Home No', 'Mobile No', 'Enroll for Semester', 'Intake', 'Password'),
('x', 'y', 'p', 'c', 'v', 'b', 'n', 'm');

-- --------------------------------------------------------

--
-- Table structure for table `studentv1`
--

CREATE TABLE `studentv1` (
  `pid` int(12) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_subject`
--

CREATE TABLE `student_subject` (
  `ss_code` varchar(100) NOT NULL,
  `sub_code` varchar(100) NOT NULL,
  `student_id` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_subject`
--

INSERT INTO `student_subject` (`ss_code`, `sub_code`, `student_id`) VALUES
('temp', '201', '2017'),
('temp', '301', '2017'),
('temp', '201', '2016'),
('temp1', '302', 'p'),
('3333', 'subjectp', '2013'),
('subjectp', '3333', '2013');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `sub_code` varchar(20) NOT NULL,
  `sub_name` varchar(100) NOT NULL,
  `sub_credit` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`sub_code`, `sub_name`, `sub_credit`) VALUES
('201', 'C programming', '3'),
('301', 'Compiler', '3'),
('333', 'pkkkkchooo', '3');

-- --------------------------------------------------------

--
-- Table structure for table `temp_student`
--

CREATE TABLE `temp_student` (
  `student_name_t` varchar(100) NOT NULL,
  `passport_t` varchar(100) NOT NULL,
  `student_id_t` varchar(100) NOT NULL,
  `home_no_t` varchar(100) NOT NULL,
  `mobile_no_t` varchar(100) NOT NULL,
  `semester_enroll_t` varchar(100) NOT NULL,
  `intake_t` varchar(100) NOT NULL,
  `password_t` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `temp_student_subject`
--

CREATE TABLE `temp_student_subject` (
  `ss_code_t` varchar(100) NOT NULL,
  `sub_code_t` varchar(100) NOT NULL,
  `student_id_t` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `temp_student_subject`
--

INSERT INTO `temp_student_subject` (`ss_code_t`, `sub_code_t`, `student_id_t`) VALUES
('100howcum', '100', 'howcum'),
('C programming', '201', 'Username'),
('Compiler', '301', 'Username');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `fullname` varchar(80) COLLATE latin1_general_ci NOT NULL,
  `username` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `password` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `phone` varchar(40) COLLATE latin1_general_ci NOT NULL,
  `email` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `role` varchar(100) COLLATE latin1_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fullname`, `username`, `password`, `phone`, `email`, `role`) VALUES
(65, '', '', '', '', '', ''),
(66, 'f', 'g', 'h', 'i', 'j', ''),
(64, 'a', 'b', 'c', 'd', 'e', '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `role`) VALUES
('admin', '123', 'admin'),
('choco', '1', 'admin'),
('email', 'password', 'registry'),
('emaildean', 'password', 'dean'),
('pleader', '1', 'program leader');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `semester`
--
ALTER TABLE `semester`
  ADD PRIMARY KEY (`sem_code`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `studentv1`
--
ALTER TABLE `studentv1`
  ADD PRIMARY KEY (`pid`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`sub_code`);

--
-- Indexes for table `temp_student`
--
ALTER TABLE `temp_student`
  ADD PRIMARY KEY (`student_id_t`);

--
-- Indexes for table `temp_student_subject`
--
ALTER TABLE `temp_student_subject`
  ADD PRIMARY KEY (`ss_code_t`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `studentv1`
--
ALTER TABLE `studentv1`
  MODIFY `pid` int(12) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
