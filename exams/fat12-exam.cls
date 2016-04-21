\NeedsTeXFormat{LaTeX2e}
\ProvidesPackage{khlexamen}[04/07/2014]
\LoadClass{scrartcl}

\RequirePackage[paper=a4paper,headheight=1.2cm,top=2cm,bottom=1.5cm,includeheadfoot]{geometry}
\RequirePackage{a4wide}
\RequirePackage{scrpage2}
\RequirePackage{afterpage}
\RequirePackage{tabularx}
\RequirePackage{booktabs}
\RequirePackage{graphicx}
\RequirePackage{multirow}
\RequirePackage{microtype}
\RequirePackage{lastpage}
\RequirePackage{tikz}
\RequirePackage{siunitx}
\RequirePackage{amsmath}
\RequirePackage{amsfonts}
\RequirePackage[utf8]{inputenc}
\RequirePackage{stmaryrd}
\RequirePackage{marvosym}
\RequirePackage{framed}
\RequirePackage{etoolbox}

\newtoggle{solution}

\ProcessOptions\relax

\newcommand{\ALT}[2]{\iftoggle{solution}{#2}{#1}}

\ifdefined\solution\toggletrue{solution}\else\togglefalse{solution}\fi


\newpagestyle{exampage}{%
  {\smallheader@produce}%
  {\smallheader@produce}%
  {\smallheader@produce}
  (\textwidth,1pt)
}{%
  {L}%
  {R}%
  {\normalfont\rlap{\exam@datum}}%
}

\newpagestyle{firstexampage}{%
  {\header@produce}%
  {\header@produce}%
  {\header@produce}
  (\textwidth,1pt)
}{%
  {L}%
  {R}%
  {\normalfont\rlap{\exam@datum}}%
}

\pagestyle{exampage}

\AtBeginDocument{
  \thispagestyle{firstexampage}
}



\pgfkeys{
  /exam/.cd,
  academiejaar/.store in=\exam@academiejaar,
  opleiding/.store in=\exam@opleiding,
  fase/.store in=\exam@fase,
  examinator/.store in=\exam@examinator,
  opo/.store in=\exam@opo,
  ola/.store in=\exam@ola,
  activiteit/.store in=\exam@activiteit,
  hulpmiddelen/.store in=\exam@hulpmiddelen,
  datum/.store in=\exam@datum,
  beginuur/.store in=\exam@beginuur,
  duur/.store in=\exam@duur,
  fill in/width/.initial={2cm},
  fill in/depth/.initial={-3mm},
  fill in/height/.initial={5mm},
  fill in/thickness/.initial={.5pt},
  fill in/solution fit width flag/.initial=0,
  fill in/solution fit width/.style={/exam/fill in/solution fit width flag=1},
  fill in/fit height/.style={/exam/fill in/height=0}
}


\newcommand{\fillindots}[1]{\makebox[#1]{\dotfill}}
\newcommand{\exam}[1]{\pgfkeys{/exam/.cd,#1}}



\newcommand{\header@produce}{%
  \footnotesize\normalfont%
  \begin{tabular}{rlr}
    Academiejaar: & \exam@academiejaar & \multirow{4}{2cm}[0mm]{\includegraphics[width=3cm]{ucll-logo.png}} \\
    Opleiding: & \exam@opleiding  & \\
    Fase: & \exam@fase  & \\
    Examinator: & \exam@examinator  & \\
    OPO: & \exam@opo  & \\
    OLA: & \exam@ola  & \\
    Activiteit:& \exam@activiteit &\\
    Hulpmiddelen: &\exam@hulpmiddelen & \\ 
    Datum: & \exam@datum &  \\
    Beginuur: & \exam@beginuur & \\
    Tijdsduur: & \exam@duur  & \\ \\
    Student: & \multicolumn{2}{l}{\parbox{5cm}{\dotfill} \hspace{1mm} Nummer: \parbox{2cm}{\dotfill} \hspace{1mm} Reeks: \parbox{1cm}{\dotfill}}
  \end{tabular}%
}

\newcommand{\smallheader@produce}{
  \footnotesize\normalfont\hspace*{\fill}
  \begin{tabular}{rlr}
    OLA: & \exam@ola  & \\
    Datum: & \exam@datum &  \\[2mm]
    Student: & \multicolumn{2}{l}{\parbox[b][.5cm][b]{9cm}{\dotfill} \hspace{1mm} \hspace{1mm} Reeks: \parbox[b][.5cm][b]{1cm}{\dotfill}}
 \end{tabular}\hspace*{\fill}%
}




\newcommand{\MAINHEADER}{\header@produce}
\newcommand{\HEADER}{\smallheader@produce}


\usetikzlibrary{patterns,calc,math}

\pgfkeys{
  /tikz/.cd,
  venn diagram/.style={thick},
  /venn diagram/.cd,
  blank/.style={fill=white},
  transparent/.style={fill=none},
  highlight/.style={fill=gray!50}
}

\newcommand{\firstpagegeometry}{\newgeometry{includeheadfoot,headheight=5cm,top=2cm,bottom=1.5cm}}
\newcommand{\normalpagegeometry}{\restoregeometry}



\newcommand{\directoryentryform}[1]{
  \begin{center}
    \begin{tikzpicture}
      \foreach \x in {0,...,15} {
        \foreach \y in {0,1} {
          \tikzmath{
            int \i;
            \i = \x + (1 - \y) * 16;
            \byte = {#1}[\i];
          }
          \node[draw,minimum width=1cm,minimum height=1cm,anchor=south west] at (\x, \y) {\ALT{}{\byte}};
        }
      }

      \draw[|-|] (0,2.2) -- (8,2.2);
      \node[anchor=base,yshift=2mm] at (4,2.2) {\sc\tiny bestandsnaam};

      \draw[|-|] (8,2.2) -- (11,2.2);
      \node[anchor=base,yshift=2mm] at (9.5,2.2) {\sc\tiny extensie};

      \draw[|-|] (11,2.2) -- (12,2.2);
      \node[anchor=base,yshift=2mm] at (11.5,2.2) {\sc\tiny attributen};

      \draw[|-|] (6,-0.2) -- (8,-0.2);
      \node[anchor=base,yshift=-3mm] at (7,-0.2) {\sc\tiny \parbox{2cm}{\centering tijd laatste write}};

      \draw[|-|] (8,-0.2) -- (10,-0.2);
      \node[anchor=base,yshift=-3mm] at (9,-0.2) {\sc\tiny \parbox{2cm}{\centering datum laatste write}};

      \draw[|-|] (10,-0.2) -- (12,-0.2);
      \node[anchor=base,yshift=-3mm] at (11,-0.2) {\sc\tiny startcluster};

      \draw[|-|] (12,-0.2) -- (16,-0.2);
      \node[anchor=base,yshift=-3mm] at (14,-0.2) {\sc\tiny grootte};
    \end{tikzpicture}
  \end{center}
}

\newcommand{\diskoverview}{
  \begin{center}
    \begin{tikzpicture}[part/.style={minimum width=5cm,minimum height=1cm,draw}]
      \begin{scope}[rotate=90,transform shape]
        \node[part] (boot sector) {boot sector};
        \node[part,anchor=north,yshift=-1mm] (fat 1) at (boot sector.south) {FAT 1};
        \node[part,anchor=north,yshift=-1mm] (fat 2) at (fat 1.south) {FAT 2};
        \node[minimum width=5cm,minimum height=1cm,anchor=north,yshift=-1mm] (fat dots) at (fat 2.south) {\dots};
        \node[part,anchor=north,yshift=-1mm] (fat n) at (fat dots.south) {FAT $n$};
        \node[part,anchor=north,yshift=-1mm] (root dir) at (fat n.south) {root directory};
        \node[part,anchor=north,yshift=-1mm] (data) at (root dir.south) {clusters (starting with \#2)};
      \end{scope}
    \end{tikzpicture}
  \end{center}
}

\newcommand{\bootsector}{\bgroup
  \newcommand{\entry}[3]{\HEX{##1} & ##2 & ##3 \tabularnewline}
  \begin{center}
    \begin{tabular}{ccl}
      \textbf{Offset} & \textbf{Length} & \textbf{Description} \tabularnewline
      \toprule
      \entry{00}{3}{Instructions to jump to the bootstrap code}
      \midrule
      \entry{03}{8}{Name of the formatting OS}
      \midrule
      \entry{0B}{2}{Bytes per sector}
      \midrule
      \entry{0D}{1}{Sectors per cluster}
      \midrule
      \entry{0E}{2}{Reserved sectors from the start of the volume}
      \midrule
      \entry{10}{1}{Number of FAT copies}
      \midrule
      \entry{11}{2}{Number of possible root entries}
      \midrule
      \entry{13}{2}{Small number of sectors}
      \midrule
      \entry{15}{1}{Media descriptor}
      \midrule
      \entry{16}{2}{Sectors per FAT}
      \midrule
      \entry{18}{2}{Sectors per track}
      \midrule
      \entry{1A}{2}{Number of heads}
      \midrule
      \entry{1C}{4}{Hidden sectors}
      \midrule
      \entry{20}{4}{Large number of sectors}
      \midrule
      \entry{24}{1}{Drive number}
      \midrule
      \entry{25}{1}{Reserved}
      \midrule
      \entry{26}{1}{Extended boot signature}
      \midrule
      \entry{27}{4}{Volume serial number}
      \midrule
      \entry{2B}{11}{Volume label}
      \midrule
      \entry{36}{8}{File system type --- should be \texttt{FAT12}}
      \midrule
      \entry{3E}{448}{Bootstrap code}
      \midrule
      \entry{1FE}{2}{Boot sector signature --- should be \HEX{AA55}}
      \bottomrule
    \end{tabular}
  \end{center}
  \egroup
}


\newcommand{\directoryentry}{\bgroup
  \newcommand{\entry}[3]{\HEX{##1} & ##2 & ##3 \tabularnewline}
  \begin{figure}[h!]
    \begin{center}
      \begin{tabular}{ccl}
        {\bf Offset} & {\bf Length} & {\bf Description} \\
        \toprule
        \entry{00}{8}{Filename}
        \midrule
        \entry{08}{3}{Extension}
        \midrule
        \entry{0B}{1}{Attributes}
        \midrule
        \entry{0C}{1}{Reserved}
        \midrule
        \entry{0D}{1}{Creation --- millisecond stamp}
        \midrule
        \entry{0E}{2}{Creation time}
        \midrule
        \entry{10}{2}{Creation date}
        \midrule
        \entry{12}{2}{Last access date}
        \midrule
        \entry{14}{2}{Reserved}
        \midrule
        \entry{16}{2}{Last write time}
        \midrule
        \entry{18}{2}{Last write date}
        \midrule
        \entry{1A}{2}{First cluster}
        \midrule
        \entry{1C}{4}{File size in bytes}
        \bottomrule
      \end{tabular}
    \end{center}
    \caption{FAT12 Directory Entry}
  \end{figure}\egroup
}

\newcommand{\attributes}{
  \begin{figure}[h!]
    \begin{center}
      \begin{tikzpicture}
        \foreach \i/\text in {0/read-only,1/hidden,2/system file,3/volume label,4/directory,5/archived,6,7} {
          \pgfmathparse{3.5-\i/2}\let\x\pgfmathresult
          \pgfmathparse{1+0.5*\i}\let\y\pgfmathresult
          \coordinate (bit \i) at (\x,0);
          \draw[black] (bit \i) rectangle +(.45,.45);
          \ifnum\i<5
            \node[anchor=west] (attr 0) at (4, \y) {\textsc{\text}};
            \draw[->,thin] (bit \i) +(0.225,0.45) |- (attr 0.west);
          \fi
        }
      \end{tikzpicture}
    \end{center}
    \caption{Attributes}
  \end{figure}
}

\newcommand{\timedate}{
\begin{figure}[h!]
  \begin{center}
    \begin{tikzpicture}
      \begin{scope}
        \foreach \i in {0,...,15} {
          \pgfmathparse{\i*.5}\let\x\pgfmathresult
          \coordinate (bit \i) at (\x,0);
          \draw[black] (bit \i) rectangle +(.45,.45);
        }

        \draw[|-|] (0,.6) -- +(2.45,0);
        \node[anchor=base] at (1.225, .65) {\textsc{hours}};

        \draw[|-|] (2.5,.6) -- +(2.95,0);
        \node[anchor=base] at (3.975,.65) {\textsc{minutes}};

        \draw[|-|] (5.5,.6) -- +(2.45,0);
        \node[anchor=base] at (6.725,.65) {\textsc{seconds/2}};
      \end{scope}

      \begin{scope}[yshift=-2cm]
        \foreach \i in {0,...,15} {
          \pgfmathparse{\i*.5}\let\x\pgfmathresult
          \coordinate (bit \i) at (\x,0);
          \draw[black] (bit \i) rectangle +(.45,.45);
        }

        \draw[|-|] (0,.6) -- +(3.45,0);
        \node[anchor=base] at (1.725, .65) {\textsc{year-1980}};

        \draw[|-|] (3.5,.6) -- +(1.95,0);
        \node[anchor=base] at (4.475,.65) {\textsc{month}};

        \draw[|-|] (5.5,.6) -- +(2.45,0);
        \node[anchor=base] at (6.725,.65) {\textsc{day}};
      \end{scope}
    \end{tikzpicture}
  \end{center}
  \caption{Time \& Date}
\end{figure}
}

\newcommand{\timeform}[1]{
  \begin{center}
    \begin{tikzpicture}
      \foreach[count=\i] \bit in {#1} {
        \pgfmathparse{\i*.8}\let\x\pgfmathresult
        \node[draw,minimum width=8mm,minimum height=8mm] (bit \i) at (\x,0) {\ALT{}{\bit}};
      }

      \draw[|-|] ($ (bit 1.north west) + (0,.25) $) -- ($ (bit 5.north east) + (0,.25) $)
                 node[midway,above,font={\sc\tiny}] {hours};

      \draw[|-|] ($ (bit 6.north west) + (0,.25) $) -- ($ (bit 11.north east) + (0,.25) $)
                 node[midway,above,font={\sc\tiny}] {minutes};

      \draw[|-|] ($ (bit 12.north west) + (0,.25) $) -- ($ (bit 16.north east) + (0,.25) $)
                 node[midway,above,font={\sc\tiny}] {seconds/2};
    \end{tikzpicture}
  \end{center}
}

\newcommand{\dateform}[1]{
  \begin{center}
    \begin{tikzpicture}
      \foreach[count=\i] \bit in {#1} {
        \pgfmathparse{\i*.8}\let\x\pgfmathresult
        \node[draw,minimum width=8mm,minimum height=8mm] (bit \i) at (\x,0) {\ALT{}{\bit}};
      }

      \draw[|-|] ($ (bit 1.north west) + (0,.25) $) -- ($ (bit 7.north east) + (0,.25) $)
                 node[midway,above,font={\sc\tiny}] {year-1980};

      \draw[|-|] ($ (bit 8.north west) + (0,.25) $) -- ($ (bit 11.north east) + (0,.25) $)
                 node[midway,above,font={\sc\tiny}] {month};

      \draw[|-|] ($ (bit 12.north west) + (0,.25) $) -- ($ (bit 16.north east) + (0,.25) $)
                 node[midway,above,font={\sc\tiny}] {day};
    \end{tikzpicture}
  \end{center}
}


\newcommand{\fatstructure}{
  \begin{center}
    \begin{tikzpicture}[box/.style={minimum width=1cm,minimum height=1cm,draw,anchor=south west}]
      \begin{scope}
        \node[box] at (0,0) {\Huge B};
        \node[box] at (1,0) {\Huge C};
      \end{scope}

      \begin{scope}[xshift=2.2cm]
        \node[box] at (0,0) {\Huge Z};
        \node[box] at (1,0) {\Huge A};
      \end{scope}

      \begin{scope}[xshift=4.4cm]
        \node[box] at (0,0) {\Huge X};
        \node[box] at (1,0) {\Huge Y};
      \end{scope}

      \begin{scope}[xshift=0cm,yshift=-2cm]
        \node[box] at (0,0) {\Huge A};
        \node[box] at (1,0) {\Huge B};
        \node[box] at (2,0) {\Huge C};
      \end{scope}

      \begin{scope}[xshift=3.4cm,yshift=-2cm]
        \node[box] at (0,0) {\Huge X};
        \node[box] at (1,0) {\Huge Y};
        \node[box] at (2,0) {\Huge Z};
      \end{scope}

      \draw[->,thick] (3.2,-0.1) -- +(0,-.75);
    \end{tikzpicture}
  \end{center}
}

\newcommand{\diskstructure}{
  \begin{center}
    \begin{tikzpicture}
      \begin{scope}[rotate=90,transform shape]
        \node[minimum width=3cm,minimum height=1cm,draw] (boot) at (0,0) {boot sector};
        \node[minimum width=3cm,minimum height=1cm,draw,anchor=north] (fat-1) at ($ (boot.south) + (0,-.1) $) {FAT1};
        \node[minimum width=3cm,minimum height=1cm,draw,anchor=north] (fat-2) at ($ (fat-1.south) + (0,-.1) $) {FAT2};
        \node[minimum width=3cm,minimum height=1cm,draw,anchor=north] (fat-dots) at ($ (fat-2.south) + (0,-.1) $) {\vdots};
        \node[minimum width=3cm,minimum height=1cm,draw,anchor=north] (fat-n) at ($ (fat-dots.south) + (0,-.1) $) {FAT$n$};
        \node[minimum width=3cm,minimum height=1cm,draw,anchor=north] (root) at ($ (fat-n.south) + (0,-.1) $) {Root};
        \node[minimum width=3cm,minimum height=4cm,draw,anchor=north] (data) at ($ (root.south) + (0,-.1) $) {Data};
        \draw ($ (boot.north west) + (-0.1,0.1) $) rectangle ($ (data.south east) + (0.1,-0.1) $);
        \node[rotate=-90] at ($ (boot.north east)!0.5!(data.south east) + (0.4,0) $) {entire disk};
      \end{scope}
    \end{tikzpicture}
  \end{center}
}

\newcommand{\bootsectorform}{
  \begin{center}
    \begin{tikzpicture}
      \draw (0,0) grid (16,-2);

      \draw[|-|,yshift=2mm] (11,0) -- ++(2,0) node[midway,above,font=\tiny] {bytes/sector};
      \draw[|-|,yshift=2mm] (13,0) -- ++(1,0) node[midway,above,font=\tiny] {sectors/cluster};
      \draw[|-|,yshift=-2mm] (0,-2) -- ++(1,0) node[midway,below,font=\tiny] {\#FATs};
      \draw[|-|,yshift=-2mm] (1,-2) -- ++(2,0) node[midway,below,font=\tiny] {\#root entries};
      \draw[|-|,yshift=-2mm] (3,-2) -- ++(2,0) node[midway,below,font=\tiny] {\#sectors};
      \draw[|-|,yshift=-2mm] (6,-2) -- ++(2,0) node[midway,below,font=\tiny] {\#sectors/FAT};
    \end{tikzpicture}
  \end{center}
}

\newcommand{\HEX}[1]{\texttt{0x#1}}
