# TodoList - Android Clean Architecture

Une application de gestion de tâches (Todo List) moderne, performante et structurée selon les principes de la **Clean Architecture**. Développée avec **Jetpack Compose** et **Material Design 3**.

## 🚀 Fonctionnalités

- **Gestion complète des tâches (CRUD)** : Ajouter, modifier, supprimer et lister des tâches.
- **Organisation par onglets** : Séparez vos tâches à faire ("À faire") de celles terminées ("Terminées").
- **Statut dynamique** : Cochez une tâche pour la marquer comme terminée instantanément.
- **Interface Moderne** : Design épuré, animations fluides (AnimatedVisibility) et support du thème Material 3.
- **Validation** : Le titre de la tâche est obligatoire pour l'ajout ou la modification.

## 🏗️ Architecture du projet

Le projet suit les principes de la **Clean Architecture** pour assurer la maintenabilité et la testabilité du code :

- **Domain Layer** : Contient le modèle `Task` et l'interface `TaskRepository`. C'est le cœur métier, indépendant des frameworks.
- **Data Layer** : Contient `TaskRepositoryImpl`. Gère la persistence des données (actuellement en mémoire).
- **UI Layer** : 
    - `TaskViewModel` : Gère l'état de l'écran et la logique de présentation.
    - `TodoListScreen` : Composants Jetpack Compose pour l'interface utilisateur.

## 🛠️ Stack Technique

- **Langage** : Kotlin
- **UI Framework** : Jetpack Compose
- **Architecture** : Clean Architecture + MVVM
- **Design** : Material 3
- **Gestion d'état** : StateFlow & ViewModel

## 📦 Installation

1. **Cloner le repository** :
   ```bash
   git clone https://github.com/votre-username/TodoList.git
   ```
2. **Ouvrir le projet** :
   Lancez Android Studio et sélectionnez le dossier du projet.
3. **Synchroniser Gradle** :
   Attendez la fin de l'indexation et cliquez sur "Sync Project with Gradle Files" si nécessaire.
4. **Lancer l'application** :
   Connectez un appareil physique ou utilisez un émulateur et cliquez sur le bouton **Run**.

## 📝 À propos de l'organisation du code

- `app/src/main/java/com/example/todolist/domain` : Logique métier pure.
- `app/src/main/java/com/example/todolist/data` : Implémentation de l'accès aux données.
- `app/src/main/java/com/example/todolist/ui` : Écrans et composants graphiques.

---
Développé avec ❤️ pour apprendre Android Moderne.
