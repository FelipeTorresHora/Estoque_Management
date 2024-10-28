# dash.py
import streamlit as st
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from func import *

# Configurações do seaborn para tema escuro
sns.set_theme(style="darkgrid")

def convert_to_dataframe(query_results):
    """Converte lista de objetos SQLAlchemy para pandas.DataFrame."""
    if not query_results:
        return pd.DataFrame()
    # Extrair os atributos relevantes dos objetos SQLAlchemy
    data = [{column: getattr(item, column) for column in item.__dict__ if not column.startswith('_')} for item in query_results]
    return pd.DataFrame(data)

def main():
    st.title("Dashboard de Gerenciamento de Estoque")

    opcoes = [
        "Buscar produtos por fornecedor",
        "Buscar produtos por categoria",
        "Buscar produtos com data de validade próxima",
        "Calcular valor total das vendas",
        "Calcular margem de lucro por produto",
        "Calcular margem de lucro total",
        "Buscar produtos com alta ou baixa quantidade vendida",
        "Buscar produtos com baixo estoque"
    ]

    escolha = st.sidebar.selectbox("Selecione a opção", opcoes)

    if escolha == "Buscar produtos por fornecedor":
        fornecedor_nome = st.text_input("Nome do Fornecedor")
        if st.button("Buscar"):
            produtos = buscar_produtos_por_fornecedor(fornecedor_nome)
            df_produtos = convert_to_dataframe(produtos)
            st.write(df_produtos)
            if not df_produtos.empty:
                # Criar gráfico de barras para visualizar as quantidades de produtos
                plt.figure(figsize=(10, 6))
                sns.barplot(x='nome', y='quantidade', data=df_produtos, palette='viridis')
                plt.xticks(rotation=45)
                plt.title(f'Quantidade de Produtos do Fornecedor: {fornecedor_nome}')
                st.pyplot(plt)

    elif escolha == "Buscar produtos por categoria":
        categoria = st.text_input("Categoria")
        if st.button("Buscar"):
            produtos = buscar_produtos_por_categoria(categoria)
            df_produtos = convert_to_dataframe(produtos)
            st.write(df_produtos)
            if not df_produtos.empty:
                # Criar gráfico de barras para visualizar os preços de venda
                plt.figure(figsize=(10, 6))
                sns.barplot(x='nome', y='preco_venda', data=df_produtos, palette='magma')
                plt.xticks(rotation=45)
                plt.title(f'Preços de Venda dos Produtos na Categoria: {categoria}')
                st.pyplot(plt)

    elif escolha == "Buscar produtos com data de validade próxima":
        dias = st.number_input("Dias até a data de validade", min_value=1)
        if st.button("Buscar"):
            produtos = buscar_produtos_com_data_validade_proxima(dias)
            df_produtos = convert_to_dataframe(produtos)
            st.write(df_produtos)
            if not df_produtos.empty:
                # Gráfico de barras para produtos com data de validade próxima
                plt.figure(figsize=(10, 6))
                sns.barplot(x='nome', y='quantidade', data=df_produtos, palette='coolwarm')
                plt.xticks(rotation=45)
                plt.title(f'Produtos com Data de Validade nos Próximos {dias} Dias')
                st.pyplot(plt)

    elif escolha == "Calcular valor total das vendas":
        periodo_inicio = st.date_input("Data de Início")
        periodo_fim = st.date_input("Data de Fim")
        if st.button("Calcular"):
            total = calcular_valor_total_vendas(periodo_inicio, periodo_fim)
            st.write(f"Valor Total das Vendas no Período: {total}")

    elif escolha == "Calcular margem de lucro por produto":
        produto_id = st.number_input("ID do Produto", min_value=1)
        if st.button("Calcular"):
            margem = calcular_margem_lucro_por_produto(produto_id)
            st.write(f"Margem de Lucro: {margem}")

    elif escolha == "Calcular margem de lucro total":
        periodo_inicio = st.date_input("Data de Início")
        periodo_fim = st.date_input("Data de Fim")
        if st.button("Calcular"):
            margem_total = calcular_margem_lucro_total(periodo_inicio, periodo_fim)
            st.write(f"Margem de Lucro Total no Período: {margem_total}")

    elif escolha == "Buscar produtos com alta ou baixa quantidade vendida":
        limite = st.number_input("Limite de vendas", min_value=1)
        acima = st.checkbox("Buscar acima do limite", value=True)
        periodo_inicio = st.date_input("Data de Início")
        periodo_fim = st.date_input("Data de Fim")
        if st.button("Buscar"):
            produtos = buscar_produtos_com_vendas_acima_abaixo_limite(limite, acima, periodo_inicio, periodo_fim)
            df_produtos = convert_to_dataframe(produtos)
            st.write(df_produtos)
            if not df_produtos.empty:
                # Gráfico para quantidade vendida dos produtos
                plt.figure(figsize=(10, 6))
                sns.barplot(x='nome', y='total_vendido', data=df_produtos, palette='viridis')
                plt.xticks(rotation=45)
                plt.title(f'Produtos com {"Alta" if acima else "Baixa"} Quantidade Vendida')
                st.pyplot(plt)

    elif escolha == "Buscar produtos com baixo estoque":
        limite = st.number_input("Limite de estoque", min_value=1)
        if st.button("Buscar"):
            produtos = buscar_produtos_com_baixo_estoque(limite)
            df_produtos = convert_to_dataframe(produtos)
            st.write(df_produtos)
            if not df_produtos.empty:
                # Gráfico para visualização de produtos com baixo estoque
                plt.figure(figsize=(10, 6))
                sns.barplot(x='nome', y='quantidade', data=df_produtos, palette='flare')
                plt.xticks(rotation=45)
                plt.title(f'Produtos com Estoque Abaixo de {limite}')
                st.pyplot(plt)

if __name__ == "__main__":
    main()
