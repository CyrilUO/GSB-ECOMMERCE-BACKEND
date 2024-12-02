# 🧨 GSB E-Commerce Backend

Bienvenue dans le backend du projet **GSB E-Commerce**, une application développée avec **Spring Boot** pour la gestion d'une plateforme de commerce électronique.

---

## **Technologies et Frameworks Utilisés**

- 🖥️ **Spring Web** : Framework pour la création d'API RESTful.
- 🗄️ **Spring JDBC** : Gestion des interactions avec la base de données MySQL.
- 🔒 **Spring Security** *(si le temps le permet)* : Implémentation de la sécurité pour l'authentification et l'autorisation.
- 🛠️ **MySQL Drivers** : Pilote JDBC pour la connexion à une base de données MySQL.

---

## 📂 **Architecture du Projet**
```plaintext
com.gsb.gsbecommercebackend
├── 📁 config
│   ├── 📝 CorsConfig.java         // Configuration CORS pour autoriser les requêtes cross-origin
│   ├── 📝 SecurityConfig.java     // Configuration de la sécurité (authentification et autorisation)
├── 📁 constant
│   ├── 📝 AppConstants.java       // Déclaration des constantes globales utilisées dans l'application
├── 📁 controller
│   ├── 📝 AuthController.java     // Contrôleur pour l'authentification et la gestion des tokens
│   ├── 📝 ProductController.java  // API pour les opérations liées aux produits
│   ├── 📝 UsersController.java    // API pour la gestion des utilisateurs
├── 📁 dao
│   ├── 📝 AuthDAO.java            // DAO pour l'accès aux données d'authentification
│   ├── 📝 ProductDAO.java         // DAO pour l'accès aux données des produits
│   ├── 📝 UsersDAO.java           // DAO pour l'accès aux données des utilisateurs
├── 📁 model
│   ├── 📁 builder
│   │   ├── 📝 ProductBuilder.java // Builder pour créer des entités `Product`
│   │   ├── 📝 UsersBuilder.java   // Builder pour créer des entités `Users`
│   ├── 📝 Product.java            // Classe modèle pour les produits
│   ├── 📝 Users.java              // Classe modèle pour les utilisateurs
├── 📁 service
│   ├── 📝 AuthService.java        // Service pour la logique d'authentification
│   ├── 📝 ProductService.java     // Service pour la logique métier liée aux produits
│   ├── 📝 UsersService.java       // Service pour la logique métier liée aux utilisateurs
├── 📝 GsbEcommerceBackendApplication.java // Point d'entrée principal de l'application
├── 📁 resources
│   ├── 📁 static                  // Répertoire pour les ressources statiques (non utilisé dans ce backend)
│   ├── 📁 templates               // Répertoire pour les templates (non utilisé dans ce backend)
│   ├── 📝 application.yaml        // Configuration principale de l'application

```
---
## **☕ Prérequis**

- **Java 17+** : Le projet est basé sur Spring Boot 3.x, nécessitant Java 17 ou une version supérieure.
- **MySQL** : Base de données utilisée pour stocker les données des utilisateurs et des produits.
- **Maven** : Gestionnaire de dépendances.

---
## **📜 Installation**

1. **⚙️ Clonez le projet :**
   ```bash
   git clone https://github.com/CyrilUO/GSB-ECOMMERCE-BACKEND.git
   cd gsbEcommerceBackend

2. **🛢️️Configurez votre base de données:**
    ```bash
    spring:
    datasource:
    url: jdbc:mysql://localhost:<default:3306>/<your-db-name>
        username : <default : root>
        password: <default : root>
        driver-class-name: com.mysql.cj.jdbc.Driver
3. **🚀 Lancer l'application**
    ```bash 
   mvn spring-boot:run

4. **Clonez la partie front du projet et lire le README associé**
    ```bash
    git clone https://github.com/CyrilUO/GSB-ECOMMERCE-FRONT
---
## **Fonctionnalités**

1. **Gestion des Utilisateurs**
    - 👤 Créer, modifier et supprimer des utilisateurs.
    - 🔐 Authentification via des tokens JWT *(optionnel si Spring Security est implémenté)*.

2. **Gestion des Produits**
    - 📦 Ajouter, modifier et supprimer des produits.
    - 🔍 Rechercher des produits via des API REST.

3. **Sécurité** *(si implémentée)* :
    - 🔒 Mise en place de JWT pour l'authentification et la sécurisation des endpoints.
    - 🌐 Configuration CORS pour accepter les requêtes du frontend *(Vue.js)*.


