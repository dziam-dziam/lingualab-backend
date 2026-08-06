# LinguaLab

A full-stack platform for creating, publishing and analysing research-oriented surveys and linguistic experiments.

LinguaLab is designed for researchers who need more flexibility than standard online survey tools provide. It combines a visual survey-building interface with support for structured questions, image-based tasks and reaction-time experiments.

The project originated from practical limitations encountered while conducting linguistic research with conventional survey platforms.

## Current Features

* User registration and authentication
* Researcher dashboard
* Survey creation and editing
* Public survey publishing
* Public survey participation
* Collection and persistence of participant responses
* Text questions
* Multiple-choice questions
* Image-based questions
* Reaction-time experiments
* Recording of pressed keys and response times in milliseconds
* Participant session management
* Survey status management
* Results interface
* Image upload and management

## Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* PostgreSQL
* Bean Validation
* REST API
* Maven

### Frontend

* Angular
* TypeScript
* Angular Material
* Angular CDK
* RxJS

## Architecture

LinguaLab uses a separated Angular frontend and Spring Boot backend.

The backend follows a layered architecture consisting of controllers, services, repositories, entities, DTOs, mappers, validators and exception handling. Authentication is implemented using Spring Security and JWT.

The frontend is organised by application features, including authentication, survey builder, dashboard, public survey participation and results presentation.

## Reaction-Time Experiments

Reaction-time questions are one of the central features distinguishing LinguaLab from conventional survey applications.

For each recorded reaction, the system can store:

* the presented stimulus;
* the key pressed by the participant;
* reaction time in milliseconds;
* the recording timestamp;
* the related question;
* the participant session.

This allows researchers to combine traditional survey responses with behavioural measurements inside one application.

## Project Motivation

During my academic work, I encountered limitations in general-purpose survey platforms when designing more complex linguistic studies.

LinguaLab is intended to provide researchers with a single environment for building experiments, publishing studies, collecting participant responses and reviewing structured results.

The project combines my background in applied linguistics with full-stack software development.

## Planned Development

* Audio and video question types
* Extended experiment configuration
* Improved results visualisation
* Data export
* Advanced survey analytics
* Additional automated tests
* Docker-based local environment
* CI/CD pipeline
* Production deployment

## Repository Structure

This repository contains the backend part of LinguaLab.

The corresponding frontend repository is available on my GitHub profile.

## Status

LinguaLab is under active development.

Implemented features are documented under “Current Features”, while functionality listed under “Planned Development” should be treated as part of the project roadmap.
