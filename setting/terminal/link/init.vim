"""""""""""""""""""""""""""""""""""""""""""""
"" Base, For more information ':help option-summary'

" rendering
set encoding=utf-8

" syntax
set showmatch            " showmatch, show matching parenthesis
set cursorline           " highlight cursor line
syntax on
" color desert            " default theme

" line number
set nu                   " number
set rnu                  " relativenumber
set numberwidth=3        " number width

" status bar, replaced with 'vim-airline' plugin
" set ru                   " ruler, show cursor position on a status bar
" set ls=2                 " laststatus, show status bar, 0 (none), 1 (not display on single window), 2 (always)

" search
set hls                  " hlsearch, highlight matches, :noh to remove highlight
set ic                   " ignorecase, can search uppercase with lowercase
set smartcase            " when search is uppercase, ignore lowercase
set incsearch            " increment search, search as characters are entered

" space
set ts=2                 " tabstop
set et                   " expandtab, tab are spaces
set ai                   " autoindent
set smartindent          " add smartly based on language
set shiftwidth=2         " width on operations like >>, <<
set wrap                 " wrap lines longer than the width of the window

" file
set autoread             " automatically apply file changes outside of vim
set nobackup             " no backup file
set noswapfile           " no swap file

" env
set nocompatible         " Disable vi-compatibility, required for Vundle
set visualbell           " don't beep
set noerrorbells         " don't beep
set confirm              " raise a confirm dislogue on unsaved changes
set history=1000         " vim command history count, saved in ~/.viminfo



"""""""""""""""""""""""""""""""""""""""""""""
"" Plugin Configs

" Remap code completion to Ctrl+Space {{{2
" inoremap <C-Space> <C-n>

" git clone https://github.com/gmarik/Vundle.vim.git ~/.vim/bundle/Vundle.vim
" VundleVim Setting
" :PluginList       - lists configured plugins
" :PluginInstall    - installs plugins; append `!` to update or just
" :PluginUpdate
" :PluginSearch foo - searches for foo; append `!` to refresh local cache
" :PluginClean      - confirms removal of unused plugins; append `!` to auto-approve removal
" see :h vundle for more details or wiki for FAQ
filetype off                  " required

" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

" let Vundle manage Vundle, required
Plugin 'VundleVim/Vundle.vim'

" syntax, statusbar
Plugin 'sonph/onehalf', {'rtp': 'vim/'}   " syntax
Plugin 'scrooloose/syntastic'             " syntax check
Plugin 'vim-airline/vim-airline'          " status bar

" navigating
Plugin 'scrooloose/nerdtree'              " file view tree
Plugin 'ctrlpvim/ctrlp.vim'               " file finder
Plugin 'majutsushi/tagbar'                " show tagbar

" intellisence
" youcompleteme
"   cd ~/.vim/bundle/YouCompleteMe
"   python3 install.py --all
"   python3 -m pip install --user --upgrade pynvim
Plugin 'valloric/youcompleteme'           " autocomplete

" git
Plugin 'tpope/vim-fugitive'               " use git in vim within vim-airline
Plugin 'airblade/vim-gitgutter'           " show git diff

" language
Plugin 'fatih/vim-go'                     " golang

call vundle#end()            " required
filetype plugin indent on    " required
" To ignore plugin indent changes, instead use:
"filetype plugin on



""""""""""""""""""""""""""""""""""""""""""
"" syntax, statusbar

""" onehalfdark
color onehalfdark

""" airline
let g:airline_theme='onehalfdark'


""""""""""""""""""""""""""""""""""""""""""
"" navigating

" nerdtree
" ne -> run nerd tree
nmap ne :NERDTreeToggle<CR>
" arrow synbols
let g:NERDTreeDirArrowExpandable = '▸'
let g:NERDTreeDirArrowCollapsible = '▾'

" ctrlpvim
" Shortcuts
" - Press <F5> to purge the cache
" - Press <c-d> to switch to filename only search instead of full path.
" - Press <c-r> to switch to regexp mode.
" - Use <c-j>, <c-k> or the arrow keys to navigate the result list.
" <c-p>  -> run nerd tree
let g:ctrlp_map = '<c-p>'
let g:ctrlp_cmd = 'CtrlP'

" tagbar
" Shortcuts
"  - ctrl
" f4 -> view tag bar
nmap <F4> :TagbarToggle<CR>

" youcompleteme
let g:ycm_autoclose_preview_window_after_insertion = 1
let g:ycm_autoclose_preview_window_after_completion = 1


""""""""""""""""""""""""""""""""""""""""""
""" load file per extentions

au BufNewFile,BufRead *.java :source ~/.config/nvim/java.vim
au BufNewFile,BufRead *.go :source ~/.config/nvim/go.vim
