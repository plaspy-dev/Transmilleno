$ErrorActionPreference = 'Stop'
function New-DemoSecret {
    [Convert]::ToHexString([Security.Cryptography.RandomNumberGenerator]::GetBytes(16)).ToLowerInvariant()
}
$env:TRANSMILLENO_DEMO_ROOT = New-DemoSecret
$env:TRANSMILLENO_DEMO_LOGIN = New-DemoSecret
