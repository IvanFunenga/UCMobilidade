# UCMobilidade

#  UC Mobility System

## Overview
UC Mobility is a Java application developed for the **University of Coimbra** to manage the rental of mobility vehicles such as **bicycles**, **electric scooters**, and **e-bikes**.  
The system allows students and staff members to rent vehicles, add optional services, and calculate rental costs based on usage time and user type.

---

##  Objectives
The main goal of this project is to apply Object-Oriented Programming (OOP) concepts — such as inheritance, polymorphism, and encapsulation — in a real-world context.

---

##  Main Features
- **Load users and vehicles** from text files at startup.  
- **Load rentals** from an object file (alugueres.obj) if it exists.  
- **Create new rentals** by selecting a user, a vehicle, and optional services.  
- **List all rentals** with their total costs.  
- **Save all rentals** to the object file when exiting the program.  

---

##  User Types
- **Student**  
  - Attributes: course, university campus (Pólo I, II, or III).  
  - Normal rental rates.

- **Staff Member**  
  - Attributes: contract year.  
  - Can be **Teaching Staff** (list of faculties) or **Non-Teaching Staff** (service/department).  
  - Non-Teaching Staff receive a **50% discount** on rentals.

---

##  Vehicle Types
Each vehicle has an **identifier (4 digits)** and **GPS location**.  
Electric vehicles include a **battery level** attribute.

| Vehicle Type | Variants | Characteristics |
|---------------|-----------|----------------|
| Bicycle | Single / Double | Simple or for two persons |
| Scooter | With or Without LCD | Electric with optional screen |
| E-Bike | Fixed / Removable Battery | Electric with two battery options |

---

##  Additional Services
| Service | Cost per Day |
|----------|---------------|
| Helmet | €5.00 |
| Light  | €2.50 |

---

##  Rental Pricing
Prices are calculated **per hour** (daily rentals = 8 hours).  

| Vehicle Type | Student (€ / h) | Staff (€ / h) |
|---------------|----------------|----------------|
| Bicycle (1 person) | 1.00 | 2.00 |
| Bicycle (2 persons) | 2.00 | 3.00 |
| Scooter (no LCD) | 1.00 | 2.50 |
| Scooter (LCD) | 1.10 | 2.60 |
| E-Bike (fixed battery) | 1.25 | 2.75 |
| E-Bike (removable battery) | 1.50 | 3.00 |

---

##  UML Structure
Main classes:
- `UCMobilidade` – Main application controller.  
- `Utilizador` (abstract)  
  - `Estudante`  
  - `Funcionario`  
    - `Docente`  
    - `NaoDocente`  
- `Veiculo` (abstract)  
  - `Bicicleta`, `VeiculoEletrico` (and their variants)  
- `ServicosAdicionais`, `Capacete`, `Luz`  
- `Aluguer` – Links users, vehicles, and optional services.

---

##  Deliverables
- UML Class Diagram (PDF)  
- All `.java` source files  
- Input text files and object file  
- Javadoc documentation (ZIP)  
- Project Report (PDF)

---

##  Authors
Developed by:

- Ivan Funenga (2023222432)  
- Rodrigo Ceia (2023)  

University of Coimbra  
Course: *Programação Orientada aos Objetos* (OOP)  
Academic Year: 2025/2026  
