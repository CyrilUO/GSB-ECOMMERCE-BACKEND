# 🧨 GSB E-Commerce Backend

Bienvenue dans le backend du projet **GSB E-Commerce**, une application développée avec **Spring Boot** côté back pour la gestion d'une plateforme de commerce électronique.

---

## **Technologies et Frameworks Utilisés**

- 🖥️ **Spring Web** : Framework pour la création d'API RESTful.


- 🗄️ **Spring JDBC** : Gestion des interactions avec la base de données MySQL.


- 🔒 **Spring Security** : Implémentation de la sécurité pour l'authentification et l'autorisation via JWT.


- 🛠️ **MySQL Drivers** : Pilote JDBC pour la connexion à une base de données MySQL.


- 📦 **Docker** : Conteneurisation pour le déploiement. Non fonctionnelle sur la v1 du projet


---

## 📂 **Architecture du Projet**
```plaintext
com.gsb.gsbecommercebackend
├── 📁 authentication                  # Gestion de la sécurité et de l'authentification JWT
│   ├── 📁 config
│   │   ├── 📝 SecurityConfig.java         # Configuration de Spring Security (authentification, autorisations)
│   │   ├── 📝 CorsConfig.java             # Déclaration des règles CORS globales
│   │   ├── 📝 CustomCorsFilter.java       # Filtre personnalisé pour affiner les règles CORS
│   ├── 📁 filter
│   │   ├── 📝 JwtAuthenticationFilter.java # Interception des requêtes pour authentifier via JWT
│   ├── 📁 dto
│   │   ├── 📝 AuthRequest.java            # DTO pour encapsuler les données de login
│   ├── 📁 service
│       ├── 📝 JwtService.java             # Génération, validation et parsing des tokens JWT

├── 📁 config                          # Autres configurations transversales
│   ├── 📝 CorsConfig.java                # Fichier de config global pour CORS
│   ├── 📝 CustomCorsFilter.java          # Implémentation d’un filtre spécifique

├── 📁 constant                       # Constantes globales du projet
│   ├── 📝 AppConstants.java              # Clés, chaînes réutilisables
│   ├── 📝 OrdersConstant.java            # Constantes spécifiques aux commandes

├── 📁 controller                     # Couche contrôleur (REST API)
│   ├── 📁 deliveryAddress
│   │   ├── 📝 DeliveryAddressController.java # Contrôleur pour les adresses de livraison
│   ├── 📁 orders
│   │   ├── 📝 OrderAnalyticsController.java  # Statistiques/rapports sur les commandes
│   │   ├── 📝 OrderCRUDController.java       # CRUD des commandes
│   ├── 📁 products
│   │   ├── 📝 ProductAnalyticsController.java # Statistiques sur les produits
│   │   ├── 📝 ProductCRUDController.java      # CRUD des produits
│   ├── 📁 roles
│   │   ├── 📝 RolesController.java            # Endpoints pour la gestion des rôles
│   ├── 📁 users
│   │   ├── 📝 UsersAnalyticsController.java   # Statistiques sur les utilisateurs
│   │   ├── 📝 UsersCRUDController.java        # CRUD des utilisateurs

├── 📁 customExceptions              # Gestion des exceptions personnalisées
│   ├── 📁 orders
│   │   ├── 📝 OrderCreationException.java     # Exception spécifique à la création d'une commande
│   ├── 📁 users
│   │   ├── 📝 DaoException.java               # Exception générique DAO côté utilisateurs
│   │   ├── 📝 UsersServiceException.java      # Exception niveau service utilisateurs

├── 📁 dao                           # DAO : accès direct à la base de données
│   ├── 📁 deliveryAddress
│   │   ├── 📝 DeliveryAddressDAO.java
│   ├── 📁 orderedItem
│   │   ├── 📝 OrderedItemDAO.java
│   ├── 📁 orders
│   │   ├── 📝 OrderAnalyticsDAO.java         # Requêtes analytiques
│   │   ├── 📝 OrderDAO.java                  # CRUD commandes
│   ├── 📁 products
│   │   ├── 📝 ProductDAO.java
│   ├── 📁 roles
│   │   ├── 📝 RolesDao.java
│   ├── 📁 users
│   │   ├── 📝 UsersDAO.java
│ 

├── 📁 dto.views                    # DTOs pour les vues spécifiques (ex : résumés, statistiques)
│   ├── 📝 OrderSummaryDTO.java

├── 📁 model                        # Entités métier (données persistées)
│   ├── 📁 builder
│   │   ├── 📝 ProductBuilder.java         # Création de produits via un builder
│   │   ├── 📝 UsersBuilder.java           # Création d’utilisateurs via un builder
│   ├── 📁 deliveryAddressClass
│   │   ├── 📝 DeliveryAddress.java
│   ├── 📁 orderedItemClass
│   │   ├── 📝 OrderedItem.java
│   ├── 📁 ordersClass
│   │   ├── 📝 Order.java
│   ├── 📁 productsClass
│   │   ├── 📝 Product.java
│   ├── 📁 rolesClass
│   │   ├── 📝 Roles.java
│   ├── 📁 usersClass
│       ├── 📝 CustomUserDetails.java       # Implémentation UserDetails pour Spring Security
│       ├── 📝 Users.java

├── 📁 service                      # Logique métier (business layer)
│   ├── 📁 deliveryAddress
│   │   ├── 📝 DeliveryAddressService.java
│   ├── 📁 orders
│   │   ├── 📝 OrderAnalyticsService.java
│   │   ├── 📝 OrderService.java
│   ├── 📁 products
│   │   ├── 📝 ProductService.java
│   ├── 📁 roles
│   │   ├── 📝 RolesService.java
│   ├── 📁 users
│   │   ├── 📝 UsersService.java
│   │   ├── 📝 UsersStatsService.java
│ 

├── 📁 utils                        # Fonctions utilitaires
│   ├── 📝 PasswordEncoderUtils.java       # Encodage BCrypt
│   ├── 📝 SwaggerConfig.java              # Configuration de la doc Swagger

├── 📝 GsbEcommerceBackendApplication.java # Point d’entrée de l’application Spring Boot

├── 📁 resources
│   ├── 📁 static                          # Contenu statique (images, JS, CSS...)
│   ├── 📁 templates                       # Templates Thymeleaf (si besoin)
│   ├── 📝 application.yaml                # Configuration principale
│   ├── 📝 application-test.yaml           # Config spécifique aux tests
│   ├── 📝 data.sql                        # Données d'exemple pour l'initialisation
│   ├── 📝 schema.sql                      # Création du schéma (si utilisée)
│   ├── 📝 views.sql                       # Script SQL pour créer les vues

├── 📁 test
│   ├── 📁 dao
│   │   ├── 📝 OrderDAOTest.java           # Tests unitaires du DAO commandes
│   │   ├── 📝 OrderedItemDAOTest.java     # Tests unitaires du DAO des articles commandés
│   ├── 📝 GsbEcommerceBackendApplicationTests.java # Tests d’intégration de l’application

```
---
## **☕ Prérequis**

- **Java 17+** : Le projet est basé sur Spring Boot 3.x, nécessitant Java 17 ou une version supérieure.
- **MySQL** : Base de données utilisée pour stocker les données des utilisateurs et des produits.
- **mysql-connector** : Dépendance présente dans le fichier pom.xml. Ce pilote est nécessaire pour permettre la communication avec la DB
- **Maven** : Gestionnaire de dépendances.

---
## **📜 Installation**

1. **⚙️ Clonez le projet :**
   ```bash
   git clone https://github.com/CyrilUO/GSB-ECOMMERCE-BACKEND.git
   cd gsbEcommerceBackend
   
2. **🛢️ Configurez votre base de données MySQL**

- Jouez dans l'ordre les scripts situés dans *src / main / ressources*
  - schema.sql   -- crée les tables
  - views.sql    -- crée les vues SQL personnalisées
  - data.sql     -- insère des données initiales

3. **🔑️ Configurez la connexion à la base**
    ```bash
    spring:
     datasource:
       url: jdbc:mysql://localhost:3306/gsb_db_ecommerce
       username: root
       password: root
       driver-class-name: com.mysql.cj.jdbc.Driver

4. **🚀 Configurez la clef JWT**
- Au lancement de l'application, une clé base64 sera générée automatiquement dans la console (via JwtKeyGeneratorUtils) 
- Inserez-là dans sa variable Secret ou dans un fichier .env authentication>service>jwtservice
- Si cela ne marche pas du à la nécéssité d'avoir une clé avant la compilation, vous pouvez run ce script pour obtenir une clef (Python)
    ```bash
    import secrets
    import base64
    
    raw_key = secrets.token_bytes(32)
    
    b64_key = base64.b64encode(raw_key).decode('utf-8')
    
    print("Clé brute :", raw_key)
    print("Clé base64 :", b64_key)

    ```
- Ou en java 
    ```bash 
    import java.security.SecureRandom;
    import java.util.Base64;
    
    public class SecretKeyGenerator {
    
        public static void main(String[] args) {
            byte[] key = new byte[32];
    
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(key);
    
            String base64Key = Base64.getEncoder().encodeToString(key);
    
            System.out.println("Clé  : " + base64Key);
        }
    }
    ```

5. **Lancez l'app**

   ```bash 
   mvn spring-boot:run
   
6. **🐋 Optionnel : Docker (non fonctionnel pour le moment)
   **
   ```bash
   docker-compose up --build

7. **♻️ Clonez la partie front du projet et lire le README associé**
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
5. **Adresses**
> ➡️ /api/delivery-address/**
6. **Roles**
> /api/roles/** 

**Visualiser l'ensemble des endpoints sur Swagger**
> http://localhost:8080/swagger-ui/index.html

--- 

## **🧪 Tests**
```
--------------------------------
           mvn test:run                    
--------------------------------
mvn -Dmaven.surefire.debug test 
--------------------------------