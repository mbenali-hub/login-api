Esta es una API REST que gestiona usuarios y genera JWT tokens para su uso en al autenticación en otras aplicaciones.

El servidor firma los token con su clave privada obtenida a partir de un contenedor (.JKS) de claves generado con Keytool. 
La aplicación que quiera usar los tokens generados por esta API deberá validarlos con la clave pública. 

La API tiene las siguientes implementaciones:
  - Registrar usuarios
  - Hacer login
  - Devolver un accesToken y un refreshToken
  - Renovar el accesToken con el refreshToken

Todos los datos sensibles deben ir en el archivo .env y pasarse a la aplicacion mediante variables de entorno configuradas en el docker-compose

!!!!NO funcionará con claves generadas con openSSL!!!!
