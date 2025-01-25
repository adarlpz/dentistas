<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With');

$host = "localhost"; 
$user = "root"; 
$password = ""; 
$dbname = "dentistas"; 

$conn = new mysqli($host, $user, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode([
        "status" => "error",
        "message" => "Error de conexión a la base de datos: " . $conn->connect_error
    ]);
    exit();
}

$sql = "SELECT nombre_completo, especialidad, telefono, email, direccion, calificacion, hora_apertura, hora_cierre FROM dentistas";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $dentistas = [];

    while ($row = $result->fetch_assoc()) {
        $dentistas[] = [
            "nombre_completo" => $row["nombre_completo"],
            "especialidad" => $row["especialidad"]
        ];
    }

    echo json_encode([
        "status" => "success",
        "data" => $dentistas
    ]);
} else {
    echo json_encode([
        "status" => "success",
        "data" => [],
        "message" => "No se encontraron dentistas"
    ]);
}

$conn->close();
?>