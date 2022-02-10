# Courier-service

This application is responsible for estimating the cost involved in a short distance conceirge service. It also predicts the estimated delivery time for each package given the avaialbility of vehicles.

**Running the application**
1. Execute mvn clean package on courier-service module. CourierServiceApplication-executable.jar will be generated in /target folder 
2. Execute java -jar on the executable jar file and provide command line arguments. 
3. In order to just estimate the delivery cost use -DcalculateCost=true
4. In order to estimate the delivery time use -DcalculateDeliveryTime=true
