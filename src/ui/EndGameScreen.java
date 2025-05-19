package ui;

import database.GameDAO;
import models.RankingEntry;
import models.RoundedButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class EndGameScreen {
    private JPanel endGamePanel;

    public EndGameScreen(JFrame frame, int gameId) {
        endGamePanel = new JPanel();
        endGamePanel.setLayout(new BoxLayout(endGamePanel, BoxLayout.Y_AXIS));
        endGamePanel.setBackground(new Color(11, 20, 28));

        // Título
        JLabel titleLabel = new JLabel("TOP 10 RANKING");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Eurostile Extended", Font.BOLD, 30));
        titleLabel.setForeground(new Color(194, 159, 97));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        endGamePanel.add(titleLabel);

        String[] columnNames = {"Username", "Time"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable rankingTable = new JTable(tableModel);
        rankingTable.setFillsViewportHeight(true);
        rankingTable.setRowHeight(30);
        rankingTable.setFont(new Font("Playfair Display", Font.PLAIN, 16));
        rankingTable.setBackground(new Color(11, 20, 28));
        rankingTable.setForeground(new Color(244, 243, 238));
        rankingTable.getTableHeader().setForeground(new Color(244, 243, 238));
        rankingTable.getTableHeader().setBackground(new Color(11, 20, 28));
        rankingTable.getTableHeader().setFont(new Font("Playfair Display", Font.BOLD, 16));
        rankingTable.setShowGrid(false);
        rankingTable.setShowHorizontalLines(false);
        rankingTable.setShowVerticalLines(false);

        List<RankingEntry> topRankings = GameDAO.getTopRankings(10);
        RankingEntry currentPlayerRanking = GameDAO.getRankingOfGame(gameId);
        boolean currentInTop = false;
        int currentPlayerRowIndex = -1;

        for (RankingEntry entry : topRankings) {
            Object[] row = {entry.getUsername(), entry.getTotalTime().toString()};
            tableModel.addRow(row);
            if (entry.getUsername().equals(currentPlayerRanking.getUsername()) &&
                    entry.getTotalTime().equals(currentPlayerRanking.getTotalTime())) {
                currentInTop = true;
                currentPlayerRowIndex = tableModel.getRowCount() - 1;
            }
        }

        if (!currentInTop) {
            currentPlayerRowIndex = tableModel.getRowCount();
            tableModel.addRow(new Object[]{
                    currentPlayerRanking.getUsername(),
                    currentPlayerRanking.getTotalTime().toString()
            });
        }

        final int highlightRow = currentPlayerRowIndex;
        rankingTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row == highlightRow) {
                    c.setBackground(new Color(194, 159, 97));
                    c.setForeground(new Color(11, 20, 28));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setBackground(new Color(11, 20, 28));
                    c.setForeground(new Color(244, 243, 238));
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(rankingTable);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        endGamePanel.add(scrollPane);

        RoundedButton exitButton = new RoundedButton("Exit Game");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Playfair Display", Font.BOLD, 18));
        exitButton.setBackground(new Color(194, 159, 97));
        exitButton.setForeground(new Color(11, 20, 28));
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(250, 70));
        exitButton.setMaximumSize(new Dimension(250, 70));
        exitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        exitButton.addMouseListener(new exitButonListener(frame, exitButton));

        endGamePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        endGamePanel.add(exitButton);
    }

    private class exitButonListener extends MouseAdapter {

        private JFrame frame;
        private JButton exitButton;

        public exitButonListener(JFrame frame, JButton exitButton) {
            this.frame = frame;
            this.exitButton = exitButton;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            frame.dispose();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            this.exitButton.setBackground(new Color(27, 58, 52));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            this.exitButton.setBackground(new Color(194, 159, 97));
        }
    }

    public JPanel getEndGamePanel() {
        return endGamePanel;
    }
}