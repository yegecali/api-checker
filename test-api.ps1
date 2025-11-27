# Script de Testing para API Checker
# Ejecutar: .\test-api.ps1

$baseUrl = "http://localhost:8081/health-check"

Write-Host "🧪 Iniciando tests de API Checker..." -ForegroundColor Cyan
Write-Host ""

# Test 1: Todos los escenarios
Write-Host "1️⃣ Test: Todos los escenarios" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri $baseUrl -Method Get
    Write-Host "   Status: $($response.status)" -ForegroundColor Green
    Write-Host "   Total Checks: $($response.totalChecks)"
    Write-Host "   All Healthy: $($response.allHealthy)"
} catch {
    Write-Host "   ❌ Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 2: Ambiente PRODUCCION
Write-Host "2️⃣ Test: Ambiente PRODUCCION" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl?environment=PRODUCCION" -Method Get
    Write-Host "   Status: $($response.status)" -ForegroundColor Green
    Write-Host "   Total Checks: $($response.totalChecks)"
    foreach ($check in $response.checks) {
        Write-Host "   - $($check.name): $($check.status)"
    }
} catch {
    Write-Host "   ❌ Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 3: Ambiente QA
Write-Host "3️⃣ Test: Ambiente QA" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl?environment=QA" -Method Get
    Write-Host "   Status: $($response.status)" -ForegroundColor Green
    Write-Host "   Total Checks: $($response.totalChecks)"
    foreach ($check in $response.checks) {
        Write-Host "   - $($check.name): $($check.status)"
    }
} catch {
    Write-Host "   ❌ Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 4: Solo críticos
Write-Host "4️⃣ Test: Solo escenarios CRÍTICOS" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl?onlyCritical=true" -Method Get
    Write-Host "   Status: $($response.status)" -ForegroundColor Green
    Write-Host "   Total Checks: $($response.totalChecks)"
    foreach ($check in $response.checks) {
        Write-Host "   - $($check.name): $($check.status)"
    }
} catch {
    Write-Host "   ❌ Error: $_" -ForegroundColor Red
}
Write-Host ""

# Test 5: PRODUCCION + Solo críticos (Smoke Test)
Write-Host "5️⃣ Test: PRODUCCION + Solo críticos (Smoke Test)" -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "$baseUrl?environment=PRODUCCION&onlyCritical=true" -Method Get
    Write-Host "   Status: $($response.status)" -ForegroundColor Green
    Write-Host "   Total Checks: $($response.totalChecks)"
    foreach ($check in $response.checks) {
        Write-Host "   - $($check.name): $($check.status)"
    }
} catch {
    Write-Host "   ❌ Error: $_" -ForegroundColor Red
}
Write-Host ""

Write-Host "✅ Tests completados" -ForegroundColor Green

