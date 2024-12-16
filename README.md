# 🧨 GSB E-Commerce Backend

Bienvenue dans le backend du projet **GSB E-Commerce**, une application développée avec **Spring Boot** côté back pour la gestion d'une plateforme de commerce électronique.

---

## **Technologies et Frameworks Utilisés**

- 🖥️ **Spring Web** : Framework pour la création d'API RESTful.


- 🗄️ **Spring JDBC** : Gestion des interactions avec la base de données MySQL.


- 🔒 **Spring Security** : Implémentation de la sécurité pour l'authentification et l'autorisation via JWT.


- 🛠️ **MySQL Drivers** : Pilote JDBC pour la connexion à une base de données MySQL.


- 📦 **Docker** : Conteneurisation pour le déploiement.


---

## 📂 **Architecture du Projet**
```plaintext
com.gsb.gsbecommercebackend
├── 📁 authentication
│   ├── 📁 config
│   │   ├── 📝 SecurityConfig.java         // Configuration de Spring Security
│   │   ├── 📝 CorsConfig.java             // Configuration des règles CORS
│   │   ├── 📝 CustomCorsFilter.java       // Filtre personnalisé pour CORS
│   ├── 📁 filter
│   │   ├── 📝 JwtAuthenticationFilter.java // Filtre d'authentification JWT
│   ├── 📁 dto
│   │   ├── 📝 AuthRequest.java            // DTO pour les requêtes d'authentification
│   ├── 📁 service
│       ├── 📝 JwtService.java             // Service pour la gestion des tokens JWT
│
├── 📁 constant
│   ├── 📝 AppConstants.java               // Constantes globales
│
├── 📁 controller
│   ├── 📝 AuthController.java             // Contrôleur pour l'authentification
│   ├── 📝 OrderController.java            // Gestion des commandes
│   ├── 📝 ProductController.java          // Gestion des produits
│   ├── 📝 UsersController.java            // Gestion des utilisateurs
│
├── 📁 dao
│   ├── 📝 OrderDAO.java                   // DAO pour les commandes
│   ├── 📝 OrderedItemDAO.java             // DAO pour les articles commandés
│   ├── 📝 ProductDAO.java                 // DAO pour les produits
│   ├── 📝 UsersDAO.java                   // DAO pour les utilisateurs
│
├── 📁 model
│   ├── 📁 builder
│   │   ├── 📝 ProductBuilder.java         // Builder pour les produits
│   │   ├── 📝 UsersBuilder.java           // Builder pour les utilisateurs
│   ├── 📝 Order.java                      // Modèle pour les commandes
│   ├── 📝 OrderedItem.java                // Modèle pour les articles commandés
│   ├── 📝 Product.java                    // Modèle pour les produits
│   ├── 📝 Users.java                      // Modèle pour les utilisateurs
│
├── 📁 service
│   ├── 📝 OrderService.java               // Logique métier pour les commandes
│   ├── 📝 ProductService.java             // Logique métier pour les produits
│   ├── 📝 UsersService.java               // Logique métier pour les utilisateurs
│
├── 📁 utils
│   ├── 📝 PasswordEncoderUtils.java       // Utilitaire pour l'encodage des mots de passe
│
├── 📝 GsbEcommerceBackendApplication.java // Point d'entrée principal de l'application
│
├── 📁 resources
│   ├── 📁 static                          // Contient les ressources statiques (si nécessaire)
│   ├── 📁 templates                       // Contient les templates (si nécessaire)
│   ├── 📝 application.yaml                // Configuration de l'application (base de données, port)

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
   
4. **🐋 Ou avec Docker**
   ```bash
   docker-compose up --build

5. **Clonez la partie front du projet et lire le README associé**
    ```bash
    git clone https://github.com/CyrilUO/GSB-ECOMMERCE-FRONT
---
## **Fonctionnalités**

1. **Gestion des Utilisateurs**
    - 👤 Créer, modifier et supprimer des utilisateurs.
    - 🔐 Authentification via des tokens JWT *(optionnel si Spring Security est implémenté)*.

2. **Gestion des Produits**
    - 📦 Ajouter, modifier et supprimer des produits.

3. **Sécurité** 
    - 🔒 Mise en place de JWT pour l'authentification et la sécurisation des endpoints.
    - 🌐 Configuration CORS pour accepter les requêtes du frontend *(Vue.js)*.

4. **Gestion du panier et commandes** 
   - 🔒 Mise en place de JWT pour l'authentification et la sécurisation des endpoints.
   - 🌐 Configuration CORS pour accepter les requêtes du frontend *(Vue.js)*.

5. **Analyses des données employés** 

---

## **📬 Endpoints API Principaux**
1. **Authentification**
> ➡️ /api/auth/login
2. **Utilisateurs**  
> ➡️ /api/users/**
3. **Produits** 
> ➡️ /api/products/**
4. **Commandes**
> ➡️ /api/orders/**

--- 

## **🧪 Tests**
```
mvn test:run
