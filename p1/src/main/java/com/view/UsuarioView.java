package com.view;



import com.controller.CadastrarUsuario;
import com.model.Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UsuarioView extends JFrame {

    private JButton adicionarButton = new JButton("Adicionar Usuário");
    private JButton editarButton = new JButton("Editar Usuário");
    private JButton excluirButton = new JButton("Excluir Usuário");
    private JButton listarButton = new JButton("Listar Usuário");

    public boolean validarCPF(String cpf)
    {
        cpf = cpf.replaceAll("\\D", ""); //remover não digito

        if(cpf.length()!= 11 || cpf.matches("(\\d)\\1{10}")) return false; // numeor de chars incorreto

        int [] peso  = {10,9,8,7,6,5,4,3,2};

        int soma = 0;

        for(int i = 0;i< 9;i++)
        {
            soma += (cpf.charAt(i) - '0') *peso[i];
        }

        int digitoVerificador1 = 11 - (soma%11);

        digitoVerificador1 = (digitoVerificador1 > 9)? 0: digitoVerificador1;

        soma = 0;
        int [] peso2  = {11,10,9,8,7,6,5,4,3,2};

        for(int i = 0;i< 10;i++)
        {
            soma += (cpf.charAt(i) - '0') *peso2[i];
        }

        int digitoVerificador2 = 11 - (soma%11);
        digitoVerificador2 = (digitoVerificador2 > 9)? 0: digitoVerificador2;


    
        return (cpf.charAt(9) - '0' == digitoVerificador1) && (cpf.charAt(10) - '0' == digitoVerificador2);


    }

    public  UsuarioView(){

        setTitle("Gerenciamento de Usuários");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);



        adicionarButton.setBounds(100,50,200,30);
        editarButton.setBounds(100,100,200,30);
        excluirButton.setBounds(100,150,200,30);
        listarButton.setBounds(100,200,200,30);

        add(adicionarButton);
        add(editarButton);
        add(excluirButton);
        add(listarButton);

        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JTextField nomeField = new JTextField();
                JTextField telefoneField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField cpfField = new JTextField();
                JPasswordField senhaField = new JPasswordField();


                Object [] message = { "Nome:", nomeField,
                                      "Telefone:", telefoneField,
                                      "E-mail:", emailField,
                                      "CPF:", cpfField,
                                      "Senha:", senhaField};


                int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Usuário", JOptionPane.OK_CANCEL_OPTION);
                //int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Usuário", JOptionPane.OK_CANCEL_OPTION);

                if(option == JOptionPane.OK_OPTION)
                {
                    String nome  = nomeField.getText();
                    String telefone = telefoneField.getText();
                    String email = emailField.getText();
                    String cpf = cpfField.getText();
                    String senha = new String(senhaField.getPassword());

                    if(nome.isEmpty() || cpf.isEmpty())
                    {
                        JOptionPane.showMessageDialog(null,"Nome e CPF são obrigatórios.");
                        return ;

                    }

                    if(!validarCPF(cpf))
                    {
                        JOptionPane.showMessageDialog(null,"CPF inválido");
                        return;

                    }

                    Usuario usuario = new Usuario(nome, telefone, email, cpf, senha);

                    try{
                        CadastrarUsuario cadastrarUsuario = new CadastrarUsuario();
                        cadastrarUsuario.adicionarUsuario(usuario);
                        JOptionPane.showMessageDialog(null,"Usuário Adicionado com sucesso");

                    }catch(SQLException ex)
                    {
                        

                        JOptionPane.showMessageDialog(null, "Erro ao adicionar usuário: " + ex.getMessage());

                    }


                }



                


            };
            
        });

        editarButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                

                String cpf = JOptionPane.showInputDialog("Digite o CPF do usuário a ser editado: ");

                if(cpf == null || !validarCPF(cpf))
                {
                    JOptionPane.showMessageDialog(null,"CPF inválido");

                    return;

                }

                JTextField nomeField = new JTextField();
                JTextField telefoneField = new JTextField();
                JTextField emailField = new JTextField();
                JPasswordField senhaField = new JPasswordField();


                Object [] message = { "Nome:", nomeField,
                        "Telefone:", telefoneField,
                        "E-mail:", emailField,
                        "Senha:", senhaField};
                int option = JOptionPane.showConfirmDialog(null, message, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION);
                
                if(option == JOptionPane.OK_OPTION)
                {
                    String nome  = nomeField.getText();
                    String telefone = telefoneField.getText();
                    String email = emailField.getText();
                    String senha = new String(senhaField.getPassword());

                    Usuario usuario = new Usuario(nome, telefone, email,cpf,senha);

                    try{
                        CadastrarUsuario cadastrarUsuario = new CadastrarUsuario();
                        cadastrarUsuario.editarUsuario(cpf, usuario);
                        JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
                    }catch(SQLException ex)
                    {
                        JOptionPane.showMessageDialog(null, "erro ao editar usuário: " + ex.getMessage());


                    }


                }




            };
            
        });

        excluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String cpf = JOptionPane.showInputDialog("Digite o CPF do usuário a ser excluído:");
                if (cpf == null || !validarCPF(cpf)) {
                    JOptionPane.showMessageDialog(null, "CPF inválido.");
                    return;
                }
        
                int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuário?");

                if(confirm == JOptionPane.YES_OPTION)
                {
                    try {
                        CadastrarUsuario cadastro = new CadastrarUsuario();
                        cadastro.excluirUsuario(cpf);
                    }
                    catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir usuário: " + ex.getMessage());
                    }


                }

            };
            
        });

        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String filtro = JOptionPane.showInputDialog("Digite um filtro (nome, email ou CPF)");
                if(filtro == null) filtro = "";

                try{
                    CadastrarUsuario cadastro = new CadastrarUsuario();
                    List<Usuario> usuarios = cadastro.listarUsuarios(filtro);

                    StringBuilder sb = new StringBuilder();

                    for(Usuario usuario: usuarios)
                    {
                        sb.append("Nome: ").append(usuario.getNome());
                        sb.append(" Telefone: ").append(usuario.getTelefone());
                        sb.append(" Email: ").append(usuario.getEmail());
                        sb.append(" CPF: ").append(usuario.getCpf());
                        sb.append("\n");
                        


                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);

                    scrollPane.setPreferredSize(new java.awt.Dimension(500,500));
                    JOptionPane.showMessageDialog(null, scrollPane, "Lista de Usuários", JOptionPane.INFORMATION_MESSAGE);






                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao listar usuários: " + ex.getMessage());
                }

            };
            
        });



    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Cria a tela principal da aplicação
                UsuarioView view = new UsuarioView();
                view.setVisible(true);
            }
        });

    }

}
