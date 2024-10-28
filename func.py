# func.py
from sqlalchemy import create_engine, Table, Column, Integer, String, Float, Date, ForeignKey, func as sa_func
from sqlalchemy.orm import relationship, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
import datetime

Base = declarative_base()

# Configuração do banco de dados MySQL
DATABASE_URL = "mysql+pymysql://root:12345678@localhost/estoque"
engine = create_engine(DATABASE_URL)
Session = sessionmaker(bind=engine)

# Tabela associativa para o relacionamento muitos-para-muitos
produto_fornecedor = Table(
    'produto_fornecedor', Base.metadata,
    Column('produto_id', Integer, ForeignKey('produtos.id'), primary_key=True),
    Column('fornecedor_id', Integer, ForeignKey('fornecedores.id'), primary_key=True)
)

# Definição das classes de modelo
class Fornecedor(Base):
    __tablename__ = 'fornecedores'
    id = Column(Integer, primary_key=True)
    fornecedor_nome = Column(String)
    numero = Column(String)
    avaliacao = Column(String)
    produtos = relationship("Produto", secondary=produto_fornecedor, back_populates="fornecedores")

class Produto(Base):
    __tablename__ = 'produtos'
    id = Column(Integer, primary_key=True)
    nome = Column(String, nullable=False)
    quantidade = Column(Integer)
    preco_compra = Column(Float)
    preco_venda = Column(Float)
    categoria = Column(String, nullable=False)
    data_compra = Column(Date, nullable=False)
    data_validade = Column(Date)
    fornecedores = relationship("Fornecedor", secondary=produto_fornecedor, back_populates="produtos")

class Venda(Base):
    __tablename__ = 'orders'
    id = Column(Integer, primary_key=True)
    data_pedido = Column(Date)
    valor_total = Column(Float)
    custo_envio = Column(Float)
    itens = relationship("ItemVenda", back_populates="venda")

class ItemVenda(Base):
    __tablename__ = 'item_venda'
    id = Column(Integer, primary_key=True)
    quantidade = Column(Integer)
    preco_unitario = Column(Float)
    valor_total_item = Column(Float)
    venda_id = Column(Integer, ForeignKey('orders.id'))
    produto_id = Column(Integer, ForeignKey('produtos.id'))
    venda = relationship("Venda", back_populates="itens")
    produto = relationship("Produto")

# Funções de interação com o banco
def buscar_produtos_por_fornecedor(fornecedor_nome):
    session = Session()
    produtos = session.query(Produto).join(Produto.fornecedores).filter(Fornecedor.fornecedor_nome == fornecedor_nome).all()
    session.close()
    return produtos

def buscar_produtos_por_categoria(categoria):
    session = Session()
    produtos = session.query(Produto).filter(Produto.categoria == categoria).all()
    session.close()
    return produtos

def buscar_produtos_com_data_validade_proxima(dias):
    session = Session()
    hoje = datetime.date.today()
    data_limite = hoje + datetime.timedelta(days=dias)
    produtos = session.query(Produto).filter(Produto.data_validade <= data_limite).all()
    session.close()
    return produtos

def calcular_valor_total_vendas(periodo_inicio, periodo_fim):
    session = Session()
    total = session.query(sa_func.sum(Venda.valor_total)).filter(Venda.data_pedido.between(periodo_inicio, periodo_fim)).scalar()
    session.close()
    return total

def calcular_margem_lucro_por_produto(produto_id):
    session = Session()
    produto = session.query(Produto).filter(Produto.id == produto_id).first()
    
    if produto:
        # Evita divisão por zero
        if produto.preco_compra == 0:
            margem_lucro_percentual_produto = None
        else:
            # Calcula a margem de lucro em porcentagem
            margem_lucro = produto.preco_venda - produto.preco_compra
            margem_lucro_percentual_produto = (margem_lucro / produto.preco_compra) * 100
            margem_lucro_percentual_produto = f"{margem_lucro_percentual_produto:.2f}%"
    else:
        margem_lucro_percentual_produto = None

    session.close()
    return margem_lucro_percentual_produto

def calcular_margem_lucro_total(periodo_inicio, periodo_fim):
    session = Session()
    vendas = session.query(ItemVenda).join(Venda).filter(Venda.data_pedido.between(periodo_inicio, periodo_fim)).all()

    # Calcula o lucro total e o custo total
    lucro_total = sum([(item.produto.preco_venda - item.produto.preco_compra) * item.quantidade for item in vendas])
    custo_total = sum([item.produto.preco_compra * item.quantidade for item in vendas])

    session.close()

    # Evita divisão por zero
    if custo_total == 0:
        return 0

    # Calcula a margem de lucro em porcentagem
    margem_lucro_percentual = (lucro_total / custo_total) * 100
    margem_lucro_percentual = f"{margem_lucro_percentual:.2f}%"
    return margem_lucro_percentual

def buscar_produtos_com_vendas_acima_abaixo_limite(limite, acima=True, periodo_inicio=None, periodo_fim=None):
    session = Session()
    query = session.query(Produto, sa_func.sum(ItemVenda.quantidade).label('total_vendido')).join(ItemVenda).join(Venda)

    # Filtro por período, se fornecido
    if periodo_inicio and periodo_fim:
        query = query.filter(Venda.data_pedido.between(periodo_inicio, periodo_fim))

    # Agrupamento pelo ID do produto
    query = query.group_by(Produto.id)

    # Filtro por limite de vendas
    if acima:
        produtos = query.having(sa_func.sum(ItemVenda.quantidade) >= limite).all()
    else:
        produtos = query.having(sa_func.sum(ItemVenda.quantidade) <= limite).all()

    session.close()

    # Converter o resultado para uma lista de dicionários
    resultado_formatado = [
        {"produto": produto.nome, "total_vendido": total_vendido} for produto, total_vendido in produtos
    ]

    return resultado_formatado


def buscar_produtos_com_baixo_estoque(limite):
    session = Session()
    produtos = session.query(Produto).filter(Produto.quantidade <= limite).all()
    session.close()
    return produtos
