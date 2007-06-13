;NSIS Modern User Interface
;Hacked from one of the Example Scripts in NSIS installer

;--------------------------------
;Include Modern UI
  !include "MUI.nsh"

;--------------------------------
;General
  ;Name and file
  Name "JCCTray"
  OutFile "build\dist\installers\JCCTray-Setup-0.1b.exe"
  ;Default installation folder
  InstallDir "$PROGRAMFILES\JCCTray"
  
  ;Get installation folder from registry if available
  InstallDirRegKey HKCU "Software\JCCTray" ""

  ;Vista redirects $SMPROGRAMS to all users without this
  RequestExecutionLevel admin

;--------------------------------
;Variables
  Var MUI_TEMP
  Var STARTMENU_FOLDER

;--------------------------------
;Interface Settings
  ; The header image
  !define MUI_HEADERIMAGE
  !define MUI_HEADERIMAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Header\nsis.bmp" ; optional

  ;don't ask for confirmation on abort
  ;!define MUI_ABORTWARNING

;--------------------------------
;Pages
  ; install pages
  !insertmacro MUI_PAGE_WELCOME
  !insertmacro MUI_PAGE_LICENSE "LICENSE.txt"
  !insertmacro MUI_PAGE_DIRECTORY
  
  ;Start Menu Folder Page Configuration
  !define MUI_STARTMENUPAGE_REGISTRY_ROOT "HKCU" 
  !define MUI_STARTMENUPAGE_REGISTRY_KEY "Software\JCCTray" 
  !define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "Start Menu Folder"
  
  !insertmacro MUI_PAGE_STARTMENU Application $STARTMENU_FOLDER
  
  !insertmacro MUI_PAGE_INSTFILES
  !insertmacro MUI_PAGE_FINISH  

  ; uninstall pages
  !insertmacro MUI_UNPAGE_WELCOME
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_DIRECTORY
  !insertmacro MUI_UNPAGE_INSTFILES
  !insertmacro MUI_UNPAGE_FINISH

;--------------------------------
;Languages
  !insertmacro MUI_LANGUAGE "English"
;--------------------------------
;Some functions
Function .onInit
 ; prevent multiple instances`
 System::Call 'kernel32::CreateMutexA(i 0, i 0, t "myMutex") i .r1 ?e'
 Pop $R0
 
 StrCmp $R0 0 +3
   MessageBox MB_OK|MB_ICONEXCLAMATION "The installer is already running."
   Abort
  
 ;check previous install
  ReadRegStr $R0 HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JCCTray" "UninstallString"
  StrCmp $R0 "" done
 
  MessageBox MB_OKCANCEL|MB_ICONEXCLAMATION "JCCTray is already installed. $\n$\nClick `OK` to remove the previous version or `Cancel` to cancel this upgrade." IDOK uninst
  Abort
  
;Run the uninstaller
uninst:
  ClearErrors
  Exec $INSTDIR\Uninstall.exe ; instead of the ExecWait line
  ;ExecWait '$R0 _?=$INSTDIR' ;Do not copy the uninstaller to a temp file
 
  IfErrors no_remove_uninstaller
    Delete /REBOOTOK "$INSTDIR\Uninstall.exe"
  no_remove_uninstaller:
done:

FunctionEnd

;--------------------------------
;Installer Sections
Section "Install" InstallSection

  SetOutPath "$INSTDIR"
  
  ;ADD YOUR OWN FILES HERE...
  File /r /x *gtk*.jar build\dist\win32\*.* 

  ;Store installation folder
  WriteRegStr HKCU "Software\JCCTray" "" $INSTDIR
  
  ;Create uninstaller
  WriteUninstaller "$INSTDIR\Uninstall.exe"

  ; add to add/remove programs in the control panel
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JCCTray" "DisplayName" "JCCTray"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JCCTray" "UninstallString" "$INSTDIR\Uninstall.exe"

  ;for the current user
  ;WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\Uninstall\Product" "DisplayName" "Application Name"

  
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    ;Create shortcuts
    CreateDirectory "$SMPROGRAMS\$STARTMENU_FOLDER"
    CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\JCCTray.lnk" "$INSTDIR\JCCTray.cmd"
    CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\Uninstall.lnk" "$INSTDIR\Uninstall.exe"
  !insertmacro MUI_STARTMENU_WRITE_END

SectionEnd

;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ;ADD YOUR OWN FILES HERE...
  Delete "$INSTDIR\Uninstall.exe"
  Delete "$INSTDIR\jcctray.cmd"
  Delete "$INSTDIR\LICENSE.txt"
  RMDir /r "$INSTDIR\lib"
  RMDir "$INSTDIR"
  
  !insertmacro MUI_STARTMENU_GETFOLDER Application $MUI_TEMP
  
  ;Delete start menu parent diretories
  Delete "$SMPROGRAMS\$MUI_TEMP\Uninstall.lnk"
  Delete "$SMPROGRAMS\$MUI_TEMP\JCCTray.lnk"
  
  ;Delete empty start menu parent diretories
  StrCpy $MUI_TEMP "$SMPROGRAMS\$MUI_TEMP"
 
  startMenuDeleteLoop:
	ClearErrors
    RMDir $MUI_TEMP
    GetFullPathName $MUI_TEMP "$MUI_TEMP\.."
    
    IfErrors startMenuDeleteLoopDone
  
    StrCmp $MUI_TEMP $SMPROGRAMS startMenuDeleteLoopDone startMenuDeleteLoop
  startMenuDeleteLoopDone:

  DeleteRegKey /ifempty HKCU "Software\JCCTray"
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JCCTray"

SectionEnd
