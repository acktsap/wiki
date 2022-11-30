# OSX

- [Setting](#setting)
- [Downloads](#downloads)
  - [Utils](#utils)
  - [Dev](#dev)
  - [Browser](#browser)
  - [Docs](#docs)
  - [Etc](#etc)
- [Shortcuts](#shortcuts)
- [Tips](#tips)
  - [Set default system jdk version](#set-default-system-jdk-version)
  - [취소선 단축키](#취소선-단축키)
  - [동영상 편집](#동영상-편집)

## Setting

System preference

- Appearance
  - Dark
- Dock
  - Position on screen : Right
  - Size : 33%
  - Magnification : 50%
  - Check automatically hide and show the dock
- Sound
  - Sound Effects -> Alert volume to 0
- Keyboard
  - Modifier Keys
    - Caps Lock -> Control
    - ~~External keyboard only~~ -> Karabiner Virtual Keyboard로 대체
      - Control -> Control
      - Option -> Command
      - Command -> Optional
  - Shortcut
    - Input Sources
      - ~~Select the previous input source : Option + Space (⌥ + Space)~~ -> Karabiner Virtual Keyboard로 대체
    - Mission Control
      - [ ] Show Desktop
  - Function Keys : ${customizing}
- Trackpad
  - [] Tap to click
  - Tracking speed : 6
- Battery
  - Turn display off after 5 minutes
  - [ ] Slightly dim the display while on battery power
- Display
  - Scaled -> Larger Text랑 Default 사이

Etc

Make workspace dir & pin it to the finder

## Downloads

### Utils

Scroll Reverser

- Separate mouse and trackpad settings.
- [Download](https://pilotmoon.com/scrollreverser/)
  - Scrolling
    - Scrolling Axes
      - [X] Reverse Vertical
      - [X] Reverse Horizontal
    - Scroll Direction (Before this, set mouse scroll direction to natural)
      - [ ] Reverse Trackpad
      - [X] Reverse Mouse
    - App
      - [X] Start at login
      - [X] Shot in menu bar

Karabiner

- Virtual keyboard
- [Download](https://karabiner-elements.pqrs.org/)
- Setting
  - Karabiner Elements Preferences
    - Simple modifications
      - Apple Internal Keyboard
        - right_command to **F18**
      - External keyboard (eg. Realforce)
        - left_command to left_option
        - left_option to left_command
        - right_command to right_option
        - right_option to **F18**
    - Function keys
      - For all devices
        - [X] Use all F1, F2, etc as standard function keys
    - Devices
      - [X] Apple Internal Keyboard
      - [X] External keyboard (eg. Realforce)
  - System Preference
    - Keyboard -> Keyboard -> Modifier Keys
      - Caps Lock -> Control
    - Shortcuts -> Input Sources
      - Select the previous input source to **F18** (오른쪽 command key 누르기)
    - Input Sources
      - [ ] Use the Caps Lock key to switch to and from ABC
  - See also
    - https://ssossoblog.tistory.com/54

Alfred

- 알프레드
- [Download](https://www.alfredapp.com)
- Setting
  - System Preference
    - Keyboard Shortcuts
      - Spotlight
        - [ ] Show Spotlight search
  - Alfred Hotkey: `command + Space`

Spectacle

- A Window Manager
- [Download](https://www.spectacleapp.com/)
- Setting
  - [ ] Launch Spectacle at login

RunCat

- Monotoring tool
- [Download](https://apps.apple.com/kr/app/runcat/id1429033973?mt=12)
- Setting
  - General
    - [X] Use system accent color
    - [X] Launch Runcat at login

Hammerspoon

- Macro tool for mac.
- [Download](http://www.hammerspoon.org/)
- Setting
  - Behavior
    - [X] Launch Hammerspoon at login
    - [X] Check for updates
    - [ ] Show dock icon
    - [X] Show menu icon
    - [ ] Keep Console window on top
    - [ ] Send crash data (require restart)
  - vim hangul setting
    - Open Config -> Put code to Config -> Reload Config
      ```lua
      -- key mapping for vim 
      -- key bindding reference --> https://www.hammerspoon.org/docs/hs.hotkey.html

      local convertToEngWithEscHotKey
      convertToEngWithEscHotKey = hs.hotkey.bind({}, 'escape', function()
        local inputEnglish = "com.apple.keylayout.ABC"
        local inputSource = hs.keycodes.currentSourceID()

        if not (inputSource == inputEnglish) then
          -- 한글로 작성 중 한영전환했을 때 작성 중이던 글자가 소실되는 현상을 막기 위해
          hs.eventtap.keyStroke({}, 'right')
          hs.keycodes.currentSourceID(inputEnglish)
        end

        -- esc mapping으로 인한 재귀호출을 방지하기 위해
        convertToEngWithEscHotKey:disable()
        hs.eventtap.keyStroke({}, 'escape', 100) -- 100 ns
        convertToEngWithEscHotKey:enable()
      end)

      local convertToEngWithCtrlAndSquareBrackets
      convertToEngWithCtrlAndSquareBrackets = hs.hotkey.bind({'ctrl'}, '[', function()
        local inputEnglish = "com.apple.keylayout.ABC"
        local inputSource = hs.keycodes.currentSourceID()

        if not (inputSource == inputEnglish) then
          -- 한글로 작성 중 한영전환했을 때 작성 중이던 글자가 소실되는 현상을 막기 위해
          hs.eventtap.keyStroke({}, 'right')
          hs.keycodes.currentSourceID(inputEnglish)
        end

        -- esc mapping으로 인한 재귀호출을 방지하기 위해
        convertToEngWithCtrlAndSquareBrackets:disable()
        hs.eventtap.keyStroke({'ctrl'}, '[', 100) -- 100 ns
        convertToEngWithCtrlAndSquareBrackets:enable()
      end)
      ```
- See also
  - [Hammerspoon official](http://www.hammerspoon.org/go/)
  - [esc 키로 편하게 한영 변환하기 for vim](https://humblego.tistory.com/10)
  - [vim - normal mode에서 자동으로 한영전환하기](https://frhyme.github.io/vim/vim09_type_kor_on_command_mode/)

IINA

- Open source music player written in native Swift language.
- [Download](https://iina.io/)

Gif Brewery 3 by gfycat

- gif generator.
- [Download](https://apps.apple.com/kr/app/gif-brewery-3-by-gfycat/id1081413713?mt=12)
- Setting
  - Video location : `~/Movies/GIF Brewery 3`

### Dev

- [Iterm2](https://iterm2.com)
- [Brew](https://brew.sh/index_ko)
- [Intellij](https://www.jetbrains.com/idea/download/#section=mac)
  - [setting](./intellij.md)
- [vscode](https://code.visualstudio.com/)
  - [setting](./vscode.md)
- [install language](./language-installation.md)
- [Postman](https://www.postman.com/downloads/)

### Browser

- [Chrome](https://www.google.com/chrome/)
  - [setting](./chrome.md)
- [Whale](https://whale.naver.com/en/download/mac/)
  - [setting](./whale.md)
- [(Brave)](https://brave.com/?ref=xwv588)
  - [setting](./brave.md)
- [(Firefox)](https://www.mozilla.org/en-US/firefox/new/)
  - [setting](./firefox.md)

### Docs

- [Acrobat Pdf Reader](https://get.adobe.com/reader/)
  - [setting](./pdf-reader.md)

### Etc

- [KakaoTalk](https://www.kakaocorp.com/page/service/service/KakaoTalk?lang=ko)

See also

https://link.medium.com/1qxMH7jnMab

## Shortcuts

## Tips

### Set default system jdk version

- OSX는제일 높은 버전을 기본적으로 채택.
- 이 로직ㅇ르 disabled하는 방법. 원하는 버전 제외하고 전부 disabled 하면 됨.
- 사실 그냥 겹치는 버전이 있는 경우에만 둘중 하나 선택해서 disable하고 싶은거만 이렇게 처리.

```sh
> cd /Library/Java/JavaVirtualMachines/${javaVersion}/Content
> mv Info.plist Info.plist.disabled
```

[How to set or change the default Java (JDK) version on macOS?](https://stackoverflow.com/questions/21964709/how-to-set-or-change-the-default-java-jdk-version-on-os-x)

### 취소선 단축키

https://www.clien.net/service/board/cm_mac/13752933

### 동영상 편집

- QuickTimePlayer 사용해서 Split Clip (command + Y)을 활용해서 중간 중간 자른 후 저장
- Mp4 변환의경우 imovie 사용
