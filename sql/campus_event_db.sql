-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2026 at 06:06 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `campus_event_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `booking_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `STATUS` varchar(20) NOT NULL DEFAULT 'CONFIRMED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`booking_id`, `user_id`, `event_id`, `booking_date`, `STATUS`) VALUES
(1, 3, 1, '2026-05-16 15:47:08', 'CONFIRMED'),
(2, 4, 2, '2026-05-16 15:59:47', 'CONFIRMED'),
(3, 4, 1, '2026-05-16 15:59:51', 'CONFIRMED'),
(4, 5, 2, '2026-05-16 16:00:29', 'CANCELLED');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `NAME`, `description`) VALUES
(1, 'Conference', 'Professional conferences and industry summits'),
(2, 'Workshop', 'Hands-on learning and skill-building sessions'),
(3, 'Seminar', 'Educational talks and guest lectures'),
(4, 'Social', 'Networking events, parties, and social gatherings'),
(5, 'Sports', 'Sporting events and tournaments'),
(6, 'Cultural', 'Cultural programmes, exhibitions, and festivals');

-- --------------------------------------------------------

--
-- Table structure for table `contact_messages`
--

CREATE TABLE `contact_messages` (
  `message_id` int(11) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `message` text NOT NULL,
  `submitted_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `event_id` int(11) NOT NULL,
  `event_name` varchar(200) NOT NULL,
  `description` text DEFAULT NULL,
  `event_date` date NOT NULL,
  `event_time` time NOT NULL,
  `location` varchar(200) NOT NULL,
  `image` varchar(255) DEFAULT 'static/images/default-event.png',
  `capacity` int(11) NOT NULL DEFAULT 50,
  `category_id` int(11) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT 'UPCOMING',
  `created_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`event_id`, `event_name`, `description`, `event_date`, `event_time`, `location`, `image`, `capacity`, `category_id`, `STATUS`, `created_by`) VALUES
(1, 'Job Fair', 'Find job and Internship Opportunities, Event with image', '2026-05-16', '09:30:00', 'Nepal Block', '1778946368424_Jobfair.png', 2, 3, 'UPCOMING', 1),
(2, 'Leading Innovation And Entrepreneurship', 'Event Example without image uploaded', '2026-05-20', '10:40:00', 'UK Block', 'static/images/default-event.png', 3, 4, 'UPCOMING', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `contact` varchar(20) NOT NULL,
  `email` varchar(150) NOT NULL,
  `profile_image` varchar(255) DEFAULT 'static/images/default-avatar.png',
  `PASSWORD` varchar(255) NOT NULL,
  `dob` date DEFAULT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'student',
  `STATUS` varchar(20) NOT NULL DEFAULT 'pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `contact`, `email`, `profile_image`, `PASSWORD`, `dob`, `role`, `STATUS`, `created_at`) VALUES
(1, 'Admin CEMS', '9999999999', 'admin@cems.com', '1778945806185_admin-icon-vector.jpg', '$2a$10$Hcx7pyr07a.x3KEi.QF36.7x5nNzSH4EUpSazMAy9s5U1IO/PHq7m', NULL, 'admin', 'approved', '2026-05-16 15:36:46'),
(2, 'CoAdmin CEMS', '88888888', 'coadmin@cems.com', 'static/images/default-avatar.png', '$2a$10$xeHwU/Rv4..JQ7WHLdomfuxWWLv5T6wn0lPXQsRRFyyFnA.ACMNtO', NULL, 'co-admin', 'approved', '2026-05-16 15:38:40'),
(3, 'First Student', '7777777777', 'firststudent@cems.com', 'static/images/default-avatar.png', '$2a$10$qMM1dG1hLl4Kr4.Gh5fKmuBeQLCnBSXSEHb31ttU56mqOmBpurPDy', NULL, 'student', 'approved', '2026-05-16 15:39:33'),
(4, 'Student withImage', '6666666666', 'studentwithimage@cems.com', '1778946092145_ai-generated-9295105_640.jpg', '$2a$10$2loOi3cFoD45byf/G/1VFuu0t0QjTWYj0/AHHuNMbYI91jR6eOyuC', NULL, 'student', 'approved', '2026-05-16 15:41:32'),
(5, 'Second Student', '5555555555', 'secondstudent@cems.com', 'static/images/default-avatar.png', '$2a$10$mqnM57x0HfepIRdxycxuDuzuwXqhbHL.Y26/UduW5.EcdHW9FZQUy', NULL, 'student', 'approved', '2026-05-16 15:42:57');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`booking_id`),
  ADD UNIQUE KEY `uq_user_event` (`user_id`,`event_id`),
  ADD KEY `idx_bookings_user` (`user_id`),
  ADD KEY `idx_bookings_event` (`event_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `contact_messages`
--
ALTER TABLE `contact_messages`
  ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `fk_events_created_by` (`created_by`),
  ADD KEY `idx_events_date` (`event_date`),
  ADD KEY `idx_events_category` (`category_id`),
  ADD KEY `idx_events_status` (`STATUS`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `uq_email` (`email`),
  ADD UNIQUE KEY `uq_contact` (`contact`),
  ADD KEY `idx_users_email` (`email`),
  ADD KEY `idx_users_status` (`STATUS`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `contact_messages`
--
ALTER TABLE `contact_messages`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `event_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_bookings_event` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_bookings_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `fk_events_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_events_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
