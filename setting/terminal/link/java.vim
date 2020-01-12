" Compile
" %:p:h -> directory containing file ('head')
set makeprg=javac
set errorformat=%A%f:%l:\ %m,%-Z%p^,%-C%.%#
" nmap <F7> :w<ENTER>:make -cp %:p:h -d %:p:h %<ENTER>
nmap <F7> :w<ENTER>:!javac -encoding utf-8 -cp %:p:h -d %:p:h %<ENTER>

" Run
" %:t:r -> tail:name of file less one extension 
nmap <F11> :w<ENTER>:!javac -encoding utf-8 -cp %:p:h -d %:p:h % && java -ea -cp %:p:h %:t:r<ENTER>

" 컴파일 에러 찾아가기
map ,n :cn<ENTER>
map ,p :cp<ENTER>
map ,l :cl<ENTER>
map ,w :cw<ENTER>

" 블럭잡고 자동 주석처리
vmap ,c :s/^/\/\//g<ENTER>
vmap ,uc :s/^\/\///g<ENTER>

" TagList 설정
nnoremap <silent> <F8> :Tlist<CR>
nnoremap <silent> <F9> :w<CR>:TlistUpdate<CR>
let Tlist_Inc_Winwidth=0
let Tlist_Use_Right_Window=1

" ctags 설정
set tags=~/.javatags
set complete=.,w,b,u,t,i

" abbreviation 설정
ab sysout System.out.println();<ESC>hi
ab syserr System.out.println();<ESC>hi
ab debug if (log.isDebugEnabled()) {<CR>log.debug();<CR>}<CR><ESC>kkf(a

" references
" see also https://vim.fandom.com/wiki/Get_the_name_of_the_current_file
