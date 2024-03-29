import tkinter
calculation = ""

def add_to_calculation(symbol):
    global calculation
    calculation += str(symbol)
    text_field.delete(0.1, 'end')
    text_field.insert(0.1, calculation)


def evaluate_calculation():
    global calculation
    try:
        calculation = str(eval(calculation))
        text_field.delete(0.1, 'end')
        text_field.insert(0.1, calculation)
    except ZeroDivisionError:
        clear_screen()
        text_field.insert(0.1, 'You can\'t divide by 0!')
    except SyntaxError:
        clear_screen()
        text_field.insert(0.1, 'You\'r syntax is wrong!')
    except Exception:
        clear_screen()
        text_field.insert(0.1, 'ERROR')


def clear_screen():
    global calculation
    calculation = ""
    text_field.delete(0.1, 'end')

window = tkinter.Tk()
window.geometry('325x530')
window.minsize(325, 530)
window.maxsize(325, 530)
window.config(background='#2e2e2e')
window.title("CALCULATOR")
text_field = tkinter.Text(window,
                          height=3,
                          width=20,
                          font=('Arial', 19, 'bold'),
                          foreground='#ffffff',
                          background='grey',
                          relief=tkinter.SUNKEN,
                          bd=10,
                          pady=8,
                          padx=8)
text_field.pack()

button_1 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(1),
                          text='1', fg='#ffffff', bg='black', activeforeground='white', activebackground='black',
)
button_1.place(x=5, y=130)

button_2 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(2),
                          text='2', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_2.place(x=85, y=130)

button_3 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(3),
                          text='3', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_3.place(x=165, y=130)

button_4 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(4),
                          text='4', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_4.place(x=5, y=210)

button_5 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(5),
                          text='5', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_5.place(x=85, y=210)

button_6 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(6),
                          text='6', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_6.place(x=165, y=210)

button_7 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(7),
                          text='7', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_7.place(x=5, y=290)

button_8 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(8),
                          text='8', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_8.place(x=85, y=290)

button_9 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(9),
                          text='9', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_9.place(x=165, y=290)

button_0 = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(0),
                          text='0', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
button_0.place(x=85, y=370)

open_bracket = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation('('),
                              text='(', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
open_bracket.place(x=5, y=370)

close_bracket = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation(')'),
                               text=')', fg='#ffffff', bg='black', activeforeground='white', activebackground='black')
close_bracket.place(x=165, y=370)

plus = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation('+'),
                      text='+', fg='#ffffff', bg='#181f1f', activeforeground='white', activebackground='#181f1f')
plus.place(x=245, y=130)

minus = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation('-'),
                       text='-', fg='#ffffff', bg='#181f1f', activeforeground='white', activebackground='#181f1f')
minus.place(x=245, y=210)

multiply = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation('*'),
                          text='*', fg='#ffffff', bg='#181f1f', activeforeground='white', activebackground='#181f1f')
multiply.place(x=245, y=290)

devise = tkinter.Button(window, width=3, height=1, font=('Arial', 26), command=lambda: add_to_calculation('/'),
                        text='/', fg='#ffffff', bg='#181f1f', activeforeground='white', activebackground='#181f1f')
devise.place(x=245, y=370)

equals = tkinter.Button(window, width=7, height=1, font=('Arial', 26), command=lambda: evaluate_calculation(),
                        text='=', fg='#ffffff', bg='#181f1f', activeforeground='white', activebackground='#181f1f')
equals.place(x=165, y=450)

clear = tkinter.Button(window, width=7, height=1, font=('Arial', 26), command=lambda: clear_screen(),
                       text='C', fg='#ffffff', bg='#181f1f', activeforeground='white', activebackground='#181f1f')
clear.place(x=5, y=450)

window.mainloop()