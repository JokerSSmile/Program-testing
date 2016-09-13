triangle.exe
IF NOT ERRORLEVEL 1 GOTO err

triangle.exe 1
IF NOT ERRORLEVEL 1 GOTO err

triangle.exe 3 5
IF NOT ERRORLEVEL 1 GOTO err

triangle.exe -4 5 3
IF NOT ERRORLEVEL 2 GOTO err

triangle.exe 4 5 0
IF NOT ERRORLEVEL 2 GOTO err

triangle.exe 4 df 0
IF NOT ERRORLEVEL 2 GOTO err

triangle.exe y ee hj
IF NOT ERRORLEVEL 2 GOTO err

triangle.exe 3.5 3.5 4.5
IF NOT ERRORLEVEL 0 GOTO err

FOR /f %%i IN ('triangle.exe 3 3 3') DO SET result=%%i
IF NOT "%result%" == "Равносторонний" GOTO err

FOR /f %%i IN ('triangle.exe 3 5 3') DO SET result=%%i
IF NOT "%result%" == "Равнобедренный" GOTO err

FOR /f %%i IN ('triangle.exe 10 1 5') DO SET result=%%i
IF NOT "%result%" == "Не" GOTO err

FOR /f %%i IN ('triangle.exe 3.4 3.4 3.4') DO SET result=%%i
IF NOT "%result%" == "Равносторонний" GOTO err

FOR /f %%i IN ('triangle.exe 3.5 5 3.5') DO SET result=%%i
IF NOT "%result%" == "Равнобедренный" GOTO err

FOR /f %%i IN ('triangle.exe 10.7 1.1 5') DO SET result=%%i
IF NOT "%result%" == "Не" GOTO err

FOR /f %%i IN ('triangle.exe 3 5 4') DO SET result=%%i
IF NOT "%result%" == "Обычный" GOTO err

FOR /f %%i IN ('triangle.exe 7 6 10') DO SET result=%%i
IF NOT "%result%" == "Обычный" GOTO err

ECHO Успешно
EXIT /b
  
err
ECHO Провалено
EXIT /b