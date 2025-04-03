# ZonaFitSpringSwing

**ZonaFitSpringSwing** es una evolución del proyecto **ZonaFitSpring** que incorpora una interfaz gráfica de usuario (GUI) desarrollada con **Swing**, combinando la robustez de **Spring Boot** en el backend con la interactividad de **Swing** en el frontend.

## Características

- **Integración de Spring Boot y Swing**: Aprovecha las capacidades de Spring Boot para la lógica de negocio y la persistencia de datos, mientras que utiliza Swing para la interfaz gráfica de usuario.
- **Arquitectura modular**: Organización del código en capas para mejorar la mantenibilidad y escalabilidad.
- **Gestión de dependencias con Maven**: Facilita la incorporación y manejo de bibliotecas externas necesarias para el proyecto.
- **Interfaz gráfica interactiva**: Proporciona una experiencia de usuario más rica y accesible mediante componentes de Swing.

## Requisitos Previos

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) versión 11 o superior.
- [Apache Maven](https://maven.apache.org/) para la gestión de dependencias y construcción del proyecto.
- Un entorno de desarrollo integrado (IDE) compatible con proyectos Spring Boot y Swing, como **IntelliJ IDEA**, **Eclipse** con el plugin Spring Tools, o **Spring Tool Suite (STS)**.

## Estructura del Proyecto

La estructura del proyecto es la siguiente:

- **ZonaFitSpringSwing/**
  - **.mvn/wrapper/**  
    - Contiene los archivos necesarios para utilizar el wrapper de Maven, permitiendo construir el proyecto sin necesidad de tener Maven instalado previamente.
  - **src/**  
    - Carpeta principal que contiene el código fuente de la aplicación, organizada según las convenciones de Spring Boot y con paquetes específicos para la interfaz gráfica desarrollada con Swing.
  - **.gitattributes**  
    - Archivo de configuración para Git, utilizado para manejar aspectos específicos como la normalización de finales de línea.
  - **.gitignore**  
    - Especifica los archivos y directorios que deben ser ignorados por Git, como archivos generados automáticamente o configuraciones locales.
  - **mvnw** y **mvnw.cmd**  
    - Scripts para ejecutar Maven Wrapper en sistemas Unix y Windows, respectivamente.
  - **pom.xml**  
    - Archivo de configuración de Maven que define las dependencias, plugins y configuraciones necesarias para construir y ejecutar la aplicación.

## Instalación y Ejecución

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/AlaanD/ZonaFitSpringSwing.git

## Autor

- **Alán David Dri**  
