<?php
header('Content-Type: application/json');


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

$id_dentista = $_GET['id_dentista'];

$query = "SELECT * FROM dentistas WHERE id = $id_dentista";
$query_count = "SELECT COUNT(*) AS total FROM dentistas";

$result = $conn->query($query);
$result2 = $conn->query($query_count);

if ($result->num_rows > 0) {

    $row_count = $result2->fetch_assoc();
    $total = $row_count['total'];
    $dentista = $result->fetch_assoc();

    echo json_encode(["status" => "success", 
    "data" => $dentista, 
    "total" => $total]);
} else {
    echo json_encode(["status" => "error", "message" => "Dentista no encontrado"]);
}

$conn->close();
?>
