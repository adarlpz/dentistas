<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: POST');
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

$data = json_decode(file_get_contents("php://input"), true);

if (!$data) {
    echo json_encode([
        "status" => "error",
        "message" => "No se recibieron datos"
    ]);
    exit();
}

$nombreCompleto = $data['nombre_completo'] ?? '';
$licencia = $data['licencia'] ?? '';
$fechaNacimiento = $data['fecha_nacimiento'] ?? '';
$telefono = $data['telefono'] ?? '';
$email = $data['email'] ?? '';
$direccion = $data['direccion'] ?? '';
$calificacion = $data['calificacion'] ?? '';
$especialidad = $data['especialidad'] ?? '';
$horaApertura = $data['hora_apertura'] ?? '';
$horaCierre = $data['hora_cierre'] ?? '';

if (empty($nombreCompleto) || empty($licencia) || empty($fechaNacimiento) || empty($telefono) ||
    empty($email) || empty($direccion) || empty($calificacion) || empty($especialidad) ||
    empty($horaApertura) || empty($horaCierre)) {
    echo json_encode([
        "status" => "error",
        "message" => "Por favor, completa todos los campos"
    ]);
    exit();
}

$sql = "INSERT INTO dentistas (nombre_completo, licencia, fecha_nacimiento, telefono, email, direccion, calificacion, especialidad, hora_apertura, hora_cierre)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

$stmt = $conn->prepare($sql);

if (!$stmt) {
    echo json_encode([
        "status" => "error",
        "message" => "Error al preparar la consulta: " . $conn->error
    ]);
    exit();
}

$stmt->bind_param(
    "ssssssssss",
    $nombreCompleto,
    $licencia,
    $fechaNacimiento,
    $telefono,
    $email,
    $direccion,
    $calificacion,
    $especialidad,
    $horaApertura,
    $horaCierre
);

if ($stmt->execute()) {
    echo json_encode([
        "status" => "success",
        "message" => "Dentista registrado con éxito"
    ]);
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Error al registrar el dentista: " . $stmt->error
    ]);
}

$stmt->close();
$conn->close();
?>
