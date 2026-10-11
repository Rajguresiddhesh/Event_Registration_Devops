# Script to push local repository to GitHub
# Account: Rajguresiddhesh (siddhesh30505@gmail.com)

param(
    [string]$RepoUrl = "https://github.com/Rajguresiddhesh/Event_Registration_Devops.git"
)

Write-Host ">>> Configuring remote repository: $RepoUrl" -ForegroundColor Cyan
git remote remove origin 2>$null
git remote add origin $RepoUrl

Write-Host ">>> Pushing 'main' branch and release tags (v1.0.0)..." -ForegroundColor Cyan
git push -u origin main --tags

Write-Host ">>> Pushing 'develop' branch..." -ForegroundColor Cyan
git push -u origin develop

Write-Host "`n[SUCCESS] Successfully pushed all branches and tags to GitHub!" -ForegroundColor Green
